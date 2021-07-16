package edu.gorb.musicstudio.command.impl;

import edu.gorb.musicstudio.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        UserService service = new UserService(); //TODO
        try {
            Optional<User> user = service.findRegisteredUser(login, password);
            if (user.isPresent()) {
                request.getSession().setAttribute(SessionAttribute.USER, user.get());
                return new CommandResult(PagePath.HOME_PAGE, CommandResult.RoutingType.REDIRECT);
            } else {
                //TODO error
            }
        } catch (ServiceException e) {
            //TODO
        }
        return new CommandResult("error_page", CommandResult.RoutingType.REDIRECT);
    }
}
