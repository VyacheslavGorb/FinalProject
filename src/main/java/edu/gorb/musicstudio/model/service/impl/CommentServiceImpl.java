package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.dto.CommentDto;
import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.CommentDao;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.UserDao;
import edu.gorb.musicstudio.model.service.CommentService;
import edu.gorb.musicstudio.util.HtmlEscapeUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    private final DateTimeFormatter formatter;

    public CommentServiceImpl() {
        formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    }

    @Override
    public List<CommentDto> findCommentsForCourse(long courseId) throws ServiceException {
        CommentDao commentDao = DaoProvider.getInstance().getCommentDao();
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        List<CommentDto> result = new ArrayList<>();
        try {
            List<Comment> comments = commentDao.findCommentsByCourseId(courseId);
            for (Comment comment : comments) {
                Optional<User> optionalUser = userDao.findEntityById(comment.getStudentId());
                if (optionalUser.isEmpty()) {
                    logger.log(Level.ERROR, "User with id {} doesn't exist", comment.getStudentId());
                    continue;
                }
                User user = optionalUser.get();
                CommentDto commentDto = new CommentDto();
                commentDto.setStudentName(user.getName());
                commentDto.setStudentSurname(user.getSurname());
                commentDto.setContent(comment.getContent());
                commentDto.setDateTime(comment.getDateTime().format(formatter));
                result.add(commentDto);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for comments by course id={}", courseId);
            throw new ServiceException("Error while searching for comments by course id=" + courseId, e);
        }
        return result;
    }

    @Override
    public int addNewComment(long userId, long courseId, String content) throws ServiceException {
        LocalDateTime dateTime = LocalDateTime.now();
        CommentDao commentDao = DaoProvider.getInstance().getCommentDao();
        String escapedContent = HtmlEscapeUtil.escape(content);
        Comment comment = new Comment(0, userId, courseId, escapedContent, dateTime);
        try {
            return commentDao.insert(comment);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while adding comment content={}", content);
            throw new ServiceException("Error while adding comment content=" + content, e);
        }
    }
}
