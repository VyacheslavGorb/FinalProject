package edu.gorb.musicstudio.command.impl;

import edu.gorb.musicstudio.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.service.ServiceProvider;
import edu.gorb.musicstudio.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        UserService service = ServiceProvider.getInstance().getUserService();
        try {
            Optional<User> user = service.findRegisteredUser(login, password);
            if (user.isPresent()) {
                request.getSession().setAttribute(SessionAttribute.USER, user.get());
                return new CommandResult(PagePath.HOME_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT); //FIXME change to personal page
            } else {
                request.setAttribute("login_error", true);
                return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.RoutingType.FORWARD);
            }
        } catch (ServiceException e) {
            //TODO how to handle such exceptions
            logger.log(Level.ERROR, e.getMessage());
        }
        return new CommandResult("error_page", CommandResult.RoutingType.FORWARD); //TODO
    }
}
