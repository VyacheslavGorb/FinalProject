package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;
import edu.gorb.musicstudio.controller.command.RequestParameter;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RemoveTeacherFromCourseCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        String teacherIdParameter = request.getParameter(RequestParameter.TEACHER_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)
                || !IntegerNumberValidator.isNonNegativeIntegerNumber(teacherIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);
        long teacherId = Long.parseLong(teacherIdParameter);
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            Optional<Course> optionalCourse = courseService.findCourseById(courseId);
            if (optionalCourse.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            Optional<User> userOptional = userService.findUserById(teacherId);
            if (userOptional.isEmpty()
                    || userOptional.get().getStatus() != UserStatus.ACTIVE
                    || userOptional.get().getRole() != UserRole.TEACHER) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            courseService.removeTeacherFromCourse(courseId, teacherId);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.MANAGE_TEACHERS_COURSE_REDIRECT
                + courseId, CommandResult.RoutingType.REDIRECT);
    }
}
