package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.MailService;
import edu.gorb.musicstudio.model.service.MailSessionCreator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger();
    private static final String MAIL_CONFIG_PROPERTIES_PATH = "properties/mail.properties";
    private static final String MAIL_MESSAGE_BUNDLE = "mailcontent";
    private static final String MESSAGE_CONTENT_TYPE = "text/plain; charset=UTF-8";

    @Override
    public void sendSignUpConfirmation(long userId, String email, String token, String sessionLocale) throws ServiceException {
        Properties mailProperties = new Properties();
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(MAIL_CONFIG_PROPERTIES_PATH);
        if (fileInputStream == null) {
            logger.log(Level.ERROR, "File {} doesn't exist", MAIL_CONFIG_PROPERTIES_PATH);
            throw new ServiceException("File " + MAIL_CONFIG_PROPERTIES_PATH + " doesn't exist");
        }

        try {
            mailProperties.load(fileInputStream);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while reading mail properties. {}", e.getMessage());
            throw new ServiceException("Error while reading mail properties", e);
        }
        MailSessionCreator creator = new MailSessionCreator(mailProperties);
        Session session = creator.createSession();
        MimeMessage message = new MimeMessage(session);

        Locale locale = new Locale(sessionLocale);
        ResourceBundle mailBundle = ResourceBundle.getBundle(MAIL_MESSAGE_BUNDLE, locale);
        String subject;
        String text;
        String linkTemplate;

        try {
            subject = mailBundle.getString("mail.content.signup.subject");
            text = mailBundle.getString("mail.content.signup.message");
            linkTemplate = mailProperties.getProperty("mail.confirmation.link_template");
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, "Mail message properties not specified. {}", e.getMessage());
            throw new ServiceException("Mail message properties not specified", e);
        }

        String messageText;
        try {
            messageText = text + String.format(linkTemplate, token, userId);
        } catch (IllegalFormatException e) {
            logger.log(Level.ERROR, "Illegal link template: {}", linkTemplate);
            throw new ServiceException("Illegal link template: " + linkTemplate, e);
        }

        try {
            message.setSubject(subject);
            message.setContent(messageText, MESSAGE_CONTENT_TYPE);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Error while sending message. {}", e.getMessage());
            throw new ServiceException("Error while sending message: " + messageText, e);
        }
    }
}
