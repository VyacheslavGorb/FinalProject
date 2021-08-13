package edu.gorb.musicstudio.controller.command.impl.student;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CommentService;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PostCommentCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String content = request.getParameter(RequestParameter.CONTENT);
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);


        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter) || content == null) {
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.INVALID_REQUEST);
            return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long userId = user.getId();
        long courseId = Long.parseLong(courseIdParameter);

        CommentService commentService = ServiceProvider.getInstance().getCommentService();
        CourseService courseService = ServiceProvider.getInstance().getCourseService();

        try {
            if (courseService.findCourseById(courseId).isEmpty()) {
                session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.INVALID_REQUEST);
                return new CommandResult(PagePath.ERROR_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            commentService.addNewComment(userId, courseId, content);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        String previousQueryLine = (String) session.getAttribute(SessionAttribute.PREVIOUS_QUERY);
        return new CommandResult(previousQueryLine, CommandResult.RoutingType.REDIRECT);
    }
}
