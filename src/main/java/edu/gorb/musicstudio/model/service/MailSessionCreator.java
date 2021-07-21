package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class MailSessionCreator {
    private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_PORT = "465";
    private static final String DEFAULT_HOST = "smtp.gmail.com";
    private String smtpHost;
    private String smtpPort;
    private String userName;
    private String userPassword;
    private Properties sessionProperties;

    public MailSessionCreator(Properties configProperties) throws ServiceException {
        smtpHost = configProperties.getProperty("mail.smtp.host");
        smtpPort = configProperties.getProperty("mail.smtp.port");
        userName = configProperties.getProperty("mail.user.name");
        userPassword = configProperties.getProperty("mail.user.password");

        if(userName == null || userPassword == null){
            logger.log(Level.ERROR, "Wrong mail authentication properties");
            throw new ServiceException("Wrong mail authentication properties");
        }

        if(smtpHost == null || smtpPort == null){
            smtpHost = DEFAULT_HOST;
            smtpPort = DEFAULT_PORT;
        }

        sessionProperties = new Properties();
        sessionProperties.put("mail.smtp.host", smtpHost);
        sessionProperties.put("mail.smtp.port", smtpPort);
        sessionProperties.put("mail.smtp.socketFactory.port", smtpPort);
        sessionProperties.put("mail.smtp.auth", "true");
        sessionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    public Session createSession() {
        return Session.getDefaultInstance(sessionProperties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });
    }
}