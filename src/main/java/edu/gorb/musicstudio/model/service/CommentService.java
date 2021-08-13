package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.dto.CommentDto;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;

public interface CommentService {
    List<CommentDto> findActiveCommentsForCourse(long courseId) throws ServiceException;

    int addNewComment(long userId, long courseId, String content) throws ServiceException;
}
