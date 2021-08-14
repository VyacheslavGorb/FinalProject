package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;
import edu.gorb.musicstudio.controller.command.RequestAttribute;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManageUsersPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        UserService userService = ServiceProvider.getInstance().getUserService();
        List<User> allUsers;
        try {
            allUsers = userService.findAllUsers();
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        Map<UserRole, List<User>> userMap = allUsers.stream()
                .collect(Collectors.groupingBy(User::getRole));

        request.setAttribute(RequestAttribute.ROLE_ORDER, List.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.STUDENT));
        request.setAttribute(RequestAttribute.USER_MAP, userMap);
        return new CommandResult(PagePath.MANAGE_USERS, CommandResult.RoutingType.FORWARD);
    }
}
