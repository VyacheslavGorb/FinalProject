package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;
import edu.gorb.musicstudio.controller.command.RequestParameter;
import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CommentService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RemoveCommentCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        String commentIdParameter = request.getParameter(RequestParameter.COMMENT_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(commentIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long commentId = Long.parseLong(commentIdParameter);
        CommentService commentService = ServiceProvider.getInstance().getCommentService();
        try {
            Optional<Comment> optionalComment = commentService.findCommentById(commentId);
            if (optionalComment.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            commentService.deactivateComment(commentId);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.MANAGE_COMMENTS_REDIRECT + courseIdParameter, CommandResult.RoutingType.REDIRECT);
    }
}
