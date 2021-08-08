package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.dto.TeacherDto;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class TeacherPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String teacherIdParameter = request.getParameter(RequestParameter.TEACHER_ID);

        if(!IntegerNumberValidator.isIntegerNumber(teacherIdParameter)){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long teacherId = Long.parseLong((teacherIdParameter));

        UserService userService = ServiceProvider.getInstance().getUserService();
        Optional<TeacherDto> optionalTeacherDto;
        try{
            optionalTeacherDto = userService.findTeacherById(teacherId);
        }catch (ServiceException e){
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        if(optionalTeacherDto.isEmpty()){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        TeacherDto teacherDto = optionalTeacherDto.get();

        if(!teacherDto.isDescriptionProvided()){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.TEACHER, teacherDto);
        return new CommandResult(PagePath.TEACHER_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
