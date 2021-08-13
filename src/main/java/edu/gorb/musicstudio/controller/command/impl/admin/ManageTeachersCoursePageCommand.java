package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ManageTeachersCoursePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        List<User> teachers;
        List<User> teachersForCourse;
        Course course;
        try {
            Optional<Course> courseOptional = courseService.findCourseById(courseId);
            if (courseOptional.isEmpty() || !courseOptional.get().isActive()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            course = courseOptional.get();
            teachers = userService.findAllActiveTeachers();
            teachersForCourse = userService.findTeachersForCourse(courseId);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        teachers.removeAll(teachersForCourse);
        request.setAttribute(RequestAttribute.COURSE, course);
        request.setAttribute(RequestAttribute.NOT_ON_COURSE_TEACHERS, teachers);
        request.setAttribute(RequestAttribute.ON_COURSE_TEACHERS, teachersForCourse);
        return new CommandResult(PagePath.MANAGE_TEACHERS_COURSE, CommandResult.RoutingType.FORWARD);
    }
}
