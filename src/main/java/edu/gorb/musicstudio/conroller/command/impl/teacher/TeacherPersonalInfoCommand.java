package edu.gorb.musicstudio.conroller.command.impl.teacher;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.dto.TeacherDto;
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
        User teacher = (User) session.getAttribute(SessionAttribute.USER);

        UserService userService = ServiceProvider.getInstance().getUserService();
        Optional<TeacherDto> optionalTeacherDto;
        try{
            optionalTeacherDto = userService.findTeacherById(teacher.getId());
        }catch (ServiceException e){
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        TeacherDto teacherDto = optionalTeacherDto.get(); //Description presence is checked in filter

        if(!teacherDto.isDescriptionProvided()){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.TEACHER, teacherDto);
        return new CommandResult(PagePath.TEACHER_PERSONAL_INFO, CommandResult.RoutingType.FORWARD);
    }
}
