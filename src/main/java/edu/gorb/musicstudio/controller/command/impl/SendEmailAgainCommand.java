package edu.gorb.musicstudio.controller.command.impl;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.MailService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SendEmailAgainCommand implements Command {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public CommandResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        String login = request.getParameter(RequestParameter.LOGIN);
        if (login == null) {
            session.setAttribute(SessionAttribute.IS_SEND_EMAIL_AGAIN_ERROR, true);
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.INVALID_REQUEST);
            return new CommandResult(PagePath.SEND_EMAIL_AGAIN_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
        }

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        MailService mailService = serviceProvider.getMailService();

        Optional<User> userOptional;
        try {
            userOptional = userService.findUserByLogin(login);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while searching for user {}: {}", login, e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        if (userOptional.isEmpty()) {
            session.setAttribute(SessionAttribute.IS_SEND_EMAIL_AGAIN_ERROR, true);
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.INVALID_LOGIN);
            return new CommandResult(PagePath.SEND_EMAIL_AGAIN_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
        }

        User user = userOptional.get();

        if (user.getStatus() != UserStatus.EMAIL_NOT_CONFIRMED) {
            session.setAttribute(SessionAttribute.IS_SEND_EMAIL_AGAIN_ERROR, true);
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.EMAIL_ALREADY_CONFIRMED);
            return new CommandResult(PagePath.SEND_EMAIL_AGAIN_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
        }

        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);

        try {
            String token = userService.createUserToken(user);
            mailService.sendSignUpConfirmation(user.getId(), user.getEmail(), token, locale);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while sending email: {}", e.getMessage());
        }

        session.setAttribute(SessionAttribute.INFO_KEY, BundleKey.EMAIL_SENT);
        return new CommandResult(PagePath.INFO_PAGE, CommandResult.RoutingType.REDIRECT);
    }
}
