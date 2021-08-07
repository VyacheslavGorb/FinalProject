package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CoursePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        long courseId;
        try {
            courseId = Long.parseLong(courseIdParameter);
        } catch (NumberFormatException e) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        Optional<Course> optionalCourse;
        try {
            optionalCourse = courseService.findCourseById(courseId);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        if(optionalCourse.isEmpty()){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.COURSE, optionalCourse.get());
        return new CommandResult(PagePath.COURSE_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
