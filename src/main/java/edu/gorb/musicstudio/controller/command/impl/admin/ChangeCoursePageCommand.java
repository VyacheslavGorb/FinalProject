package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ChangeCoursePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        Course course;
        try {
            Optional<Course> optionalCourse = courseService.findCourseById(courseId);
            if(optionalCourse.isEmpty()){
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            course = optionalCourse.get();
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.COURSE, course);
        return new CommandResult(PagePath.CHANGE_COURSE, CommandResult.RoutingType.FORWARD);
    }
}
