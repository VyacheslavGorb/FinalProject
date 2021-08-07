package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.dto.CommentDto;
import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.CommentDao;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.UserDao;
import edu.gorb.musicstudio.model.service.CommentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CommentServiceImpl implements CommentService { //todo
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<CommentDto> findCommentsForCourse(long courseId) throws ServiceException {
        CommentDao commentDao = DaoProvider.getInstance().getCommentDao();
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        List<Comment> comments;
        try {
            comments = commentDao.findCommentsByCourseId(courseId);

        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for comments by course id");
            throw new ServiceException(e);
        }

        return null;
    }
}
