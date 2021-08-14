package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;

/**
 * Dao interface for comments table
 */
public interface CommentDao extends BaseDao<Comment> {
    /**
     * Finds active comments by course id
     *
     * @param teacherId id of teacher entity
     * @return list of {@link Comment} entities or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Comment> findActiveCommentsByCourseId(long teacherId) throws DaoException;

    /**
     * Deactivates comment
     *
     * @param commentId id of comment entity
     * @throws DaoException is thrown when error while query execution occurs
     */
    void deactivateComment(long commentId) throws DaoException;
}