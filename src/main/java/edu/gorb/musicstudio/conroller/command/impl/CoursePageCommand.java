package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.dto.CommentDto;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CommentService;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class CoursePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);

        if (!IntegerNumberValidator.isIntegerNumber(courseIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);

        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        Optional<Course> optionalCourse;
        try {
            optionalCourse = courseService.findCourseById(courseId);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        if (optionalCourse.isEmpty()) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        Course course = optionalCourse.get();

        CommentService commentService = ServiceProvider.getInstance().getCommentService();
        List<CommentDto> comments;
        try {
            comments = commentService.findCommentsForCourse(courseId);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.COMMENTS, comments);
        request.setAttribute(RequestAttribute.COURSE, course);
        return new CommandResult(PagePath.COURSE_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
