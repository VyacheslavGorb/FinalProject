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
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        UserService service = ServiceProvider.getInstance().getUserService();
        try {
            Optional<User> user = service.findRegisteredUser(login, password);
            if (user.isEmpty()) {
                session.setAttribute(SessionAttribute.IS_LOGIN_ERROR, true);
                session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.LOGIN_ERROR);
                return new CommandResult(PagePath.LOGIN_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }

            if (user.get().getStatus() == UserStatus.WAITING_FOR_APPROVEMENT) {
                session.setAttribute(SessionAttribute.IS_LOGIN_ERROR, true);
                session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.LOGIN_USER_NOT_APPROVED);
                return new CommandResult(PagePath.LOGIN_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }

            if (user.get().getStatus() == UserStatus.EMAIL_NOT_CONFIRMED) {
                session.setAttribute(SessionAttribute.EMAIL_NOT_CONFIRMED, true);
                return new CommandResult(PagePath.LOGIN_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }

            if (user.get().getStatus() == UserStatus.INACTIVE) {
                session.setAttribute(SessionAttribute.IS_LOGIN_ERROR, true);
                session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.LOGIN_USER_BLOCKED);
                return new CommandResult(PagePath.LOGIN_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }

            request.getSession().setAttribute(SessionAttribute.USER, user.get());
            return new CommandResult(PagePath.HOME_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT); //FIXME change to personal page

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
    }
}
