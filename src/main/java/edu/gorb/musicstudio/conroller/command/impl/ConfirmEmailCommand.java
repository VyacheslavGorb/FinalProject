package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
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
            request.setAttribute(RequestAttribute.ERROR_KEY, "error.invalid_activation_link"); //TODO
            return new CommandResult(PagePath.ERROR_EMAIL_PAGE, CommandResult.RoutingType.FORWARD);
        }

        long userId;
        try {
            userId = Long.parseLong(userIdParameter);
        } catch (IllegalArgumentException e) {
            request.setAttribute(RequestAttribute.ERROR_KEY, "error.invalid_activation_link"); //TODO
            return new CommandResult(PagePath.ERROR_EMAIL_PAGE, CommandResult.RoutingType.FORWARD);
        }

        UserService userService = ServiceProvider.getInstance().getUserService();

        Optional<UserToken> userToken;
        try {
            userToken = userService.findValidToken(tokenParameter, userId);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while searching for user token: {}", e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.FORWARD);
        }

        if (userToken.isEmpty()) {
            request.setAttribute(RequestAttribute.ERROR_KEY, "error.invalid_activation_link"); //TODO
            return new CommandResult(PagePath.ERROR_EMAIL_PAGE, CommandResult.RoutingType.FORWARD);
        }

        try {
            userService.confirmEmail(userId);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while confirming email: {}", e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.FORWARD);
        }

        return new CommandResult(PagePath.HOME_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
