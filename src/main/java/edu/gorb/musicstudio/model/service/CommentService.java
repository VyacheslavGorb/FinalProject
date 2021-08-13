package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.entity.dto.CommentDto;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> findActiveCommentsForCourse(long courseId) throws ServiceException;

    int addNewComment(long userId, long courseId, String content) throws ServiceException;

    Optional<Comment> findCommentById(long commentId) throws ServiceException;

    void deactivateComment(long commentId) throws ServiceException;
}
