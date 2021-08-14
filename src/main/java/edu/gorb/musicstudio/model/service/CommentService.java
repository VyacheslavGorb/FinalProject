package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.entity.dto.CommentDto;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    /**
     * Finds active comments for course
     *
     * @param courseId course id
     * @return list of {@link CommentDto} or empty list if no entities found
     */
    List<CommentDto> findActiveCommentsForCourse(long courseId) throws ServiceException;

    /**
     * Adds new comment
     *
     * @param userId   user id
     * @param courseId course id
     * @param content  comment content
     * @return generated comment id
     */
    long addNewComment(long userId, long courseId, String content) throws ServiceException;

    /**
     * Find comment by id
     *
     * @param commentId comment id
     * @return optional of {@link Comment}
     */
    Optional<Comment> findCommentById(long commentId) throws ServiceException;

    /**
     * Deactivates comment
     *
     * @param commentId comment id to be deactivated
     */
    void deactivateComment(long commentId) throws ServiceException;
}
