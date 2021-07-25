package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
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
    private static final String POST_HTTP_METHOD = "POST";


    @Override
    public CommandResult execute(HttpServletRequest request) {
        if (!request.getMethod().equals(POST_HTTP_METHOD)) {
            return new CommandResult(PagePath.SEND_EMAIL_AGAIN_PAGE, CommandResult.RoutingType.FORWARD);
        }

        String login = request.getParameter(RequestParameter.LOGIN);
        if (login == null) {
            request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.INVALID_REQUEST);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.FORWARD);
        }

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        MailService mailService = serviceProvider.getMailService();

        Optional<User> userOptional;
        try {
            userOptional = userService.findUserByLogin(login);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while searching for user {}: {}", login, e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.FORWARD);
        }

        if (userOptional.isEmpty()) {
            request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.INVALID_REQUEST);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.FORWARD);
        }

        User user = userOptional.get();

        if (user.getStatus() != UserStatus.EMAIL_NOT_CONFIRMED) {
            request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.EMAIL_ALREADY_CONFIRMED);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.FORWARD);
        }

        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);

        new Thread(() -> {
            try {
                String token = userService.createUserToken(user);
                mailService.sendSignUpConfirmation(user.getId(), user.getEmail(), token, locale);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Error while sending email: {}", e.getMessage());
            }
        }).start();

        return new CommandResult(PagePath.INFO_EMAIL_SENT_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);

    }
}
