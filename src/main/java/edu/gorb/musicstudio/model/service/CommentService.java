package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.dto.CommentDto;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;

public interface CommentService { //todo
    List<CommentDto> findCommentsForCourse(long courseId) throws ServiceException;
    int addNewComment(long userId, long courseId, String content) throws ServiceException;
}
