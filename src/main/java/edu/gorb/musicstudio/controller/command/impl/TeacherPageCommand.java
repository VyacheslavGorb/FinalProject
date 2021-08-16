package edu.gorb.musicstudio.controller.command.impl;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Teacher;
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

        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(teacherIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long teacherId = Long.parseLong((teacherIdParameter));

        UserService userService = ServiceProvider.getInstance().getUserService();
        Optional<Teacher> optionalTeacher;
        try {
            optionalTeacher = userService.findTeacherById(teacherId);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        if (optionalTeacher.isEmpty()) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        Teacher teacher = optionalTeacher.get();

        if (!teacher.isDescriptionProvided()) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.TEACHER, teacher);
        return new CommandResult(PagePath.TEACHER_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
