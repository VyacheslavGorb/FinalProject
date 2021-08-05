package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;

public interface CourseService {

    List<Course> findCoursesForRequest(int pageNumber,String searchParameter)
            throws ServiceException;

    int calcPagesCount(int coursesCount);

    int countCoursesForRequest(String searchParameter) throws ServiceException;
}
