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
import java.util.Optional;

public class ConfirmEmailCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String tokenParameter = request.getParameter(RequestParameter.TOKEN);
        String userIdParameter = request.getParameter(RequestParameter.ID);

        if (tokenParameter == null || userIdParameter == null) {
            request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.INVALID_ACTIVATION_LINK);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.FORWARD);
        }

        long userId;
        try {
            userId = Long.parseLong(userIdParameter);
        } catch (IllegalArgumentException e) {
            request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.INVALID_ACTIVATION_LINK);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.FORWARD);
        }

        UserService userService = ServiceProvider.getInstance().getUserService();

        Optional<UserToken> userToken;
        try {
            userToken = userService.findValidToken(tokenParameter, userId);
        } catch (ServiceException e) {
            request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.INVALID_ACTIVATION_LINK);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.FORWARD);
        }

        if (userToken.isEmpty()) {
            return new CommandResult(PagePath.ERROR_EMAIL_PAGE, CommandResult.RoutingType.FORWARD);
        }

        try {
            Optional<User> userOptional = userService.findUserById(userId);
            if (userOptional.get().getStatus() != UserStatus.EMAIL_NOT_CONFIRMED) { // userOptional always contains value
                request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.EMAIL_ALREADY_CONFIRMED);
                return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while searching for user with id {}", userId);
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.FORWARD);
        }

        try {
            userService.confirmEmail(userId);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while confirming email: {}", e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.FORWARD);
        }

        return new CommandResult(PagePath.INFO_EMAIL_CONFIRMED_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
