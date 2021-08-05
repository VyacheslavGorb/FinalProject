package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ConfirmEmailCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String tokenParameter = request.getParameter(RequestParameter.TOKEN);
        String userIdParameter = request.getParameter(RequestParameter.ID);
        HttpSession session = request.getSession();

        if (tokenParameter == null || userIdParameter == null) {
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.INVALID_ACTIVATION_LINK);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        long userId;
        try {
            userId = Long.parseLong(userIdParameter);
        } catch (IllegalArgumentException e) {
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.INVALID_ACTIVATION_LINK);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        UserService userService = ServiceProvider.getInstance().getUserService();

        Optional<UserToken> userToken;
        try {
            userToken = userService.findValidToken(tokenParameter, userId);
        } catch (ServiceException e) {
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.INVALID_ACTIVATION_LINK);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        if (userToken.isEmpty()) {
            return new CommandResult(PagePath.ERROR_EMAIL_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        try {
            Optional<User> userOptional = userService.findUserById(userId);
            if (userOptional.get().getStatus() != UserStatus.EMAIL_NOT_CONFIRMED) { // userOptional always contains value
                session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.EMAIL_ALREADY_CONFIRMED);
                return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while searching for user with id {}", userId);
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        try {
            userService.confirmEmail(userId);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while confirming email: {}", e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        session.setAttribute(SessionAttribute.INFO_KEY, BundleKey.EMAIL_CONFIRMED);
        return new CommandResult(PagePath.INFO_PAGE, CommandResult.RoutingType.REDIRECT);
    }
}
