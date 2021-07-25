package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String POST_HTTP_METHOD = "POST";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        if (!request.getMethod().equals(POST_HTTP_METHOD)) {
            return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.RoutingType.FORWARD);
        }
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        UserService service = ServiceProvider.getInstance().getUserService();
        try {
            Optional<User> user = service.findRegisteredUser(login, password);
            if (user.isEmpty()) {
                request.setAttribute(RequestAttribute.IS_ERROR, true);
                request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.LOGIN_ERROR);
                return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.RoutingType.FORWARD);
            }

            if (user.get().getStatus() == UserStatus.EMAIL_NOT_CONFIRMED) {
                request.setAttribute(RequestAttribute.EMAIL_NOT_CONFIRMED, true);
                return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.RoutingType.FORWARD);
            }

            if (user.get().getStatus() == UserStatus.WAITING_FOR_APPROVEMENT) {
                request.setAttribute(RequestAttribute.IS_ERROR, true);
                request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.LOGIN_USER_NOT_APPROVED);
                return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.RoutingType.FORWARD);
            }

            if (user.get().getStatus() == UserStatus.INACTIVE) {
                request.setAttribute(RequestAttribute.IS_ERROR, true);
                request.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.LOGIN_USER_BLOCKED);
                return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.RoutingType.FORWARD);
            }

            request.getSession().setAttribute(SessionAttribute.USER, user.get());
            return new CommandResult(PagePath.HOME_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT); //FIXME change to personal page

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.FORWARD);
        }
    }
}
