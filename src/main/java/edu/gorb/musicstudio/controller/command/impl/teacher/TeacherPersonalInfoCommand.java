package edu.gorb.musicstudio.controller.command.impl.teacher;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Teacher;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class TeacherPersonalInfoCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);

        UserService userService = ServiceProvider.getInstance().getUserService();
        Optional<Teacher> optionalTeacher;
        try{
            optionalTeacher = userService.findTeacherById(user.getId());
        }catch (ServiceException e){
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        Teacher teacher = optionalTeacher.get(); //Description presence is checked in filter

        if(!teacher.isDescriptionProvided()){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.TEACHER, teacher);
        return new CommandResult(PagePath.TEACHER_PERSONAL_INFO, CommandResult.RoutingType.FORWARD);
    }
}
