package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.dto.CommentDto;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CommentService;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class CommentsForCoursePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        if(!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        CommentService commentService = ServiceProvider.getInstance().getCommentService();
        List<CommentDto> comments;
        try{
            Optional<Course> optionalCourse  =courseService.findCourseById(courseId);
            if(optionalCourse.isEmpty()){
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            comments = commentService.findActiveCommentsForCourse(courseId);
        }catch (ServiceException e){
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.COMMENTS, comments);
        request.setAttribute(RequestAttribute.COURSE, courseId);
        return new CommandResult(PagePath.MANAGE_COMMENTS, CommandResult.RoutingType.FORWARD);
    }
}
