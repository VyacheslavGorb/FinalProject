package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findCoursesForRequest(int pageNumber, String searchParameter)
            throws ServiceException;

    int calcPagesCount(int coursesCount);

    int countCoursesForRequest(String searchParameter) throws ServiceException;

    List<Course> trimCoursesDescriptionForPreview(List<Course> courses);

    Optional<Course> findCourseById(long courseId) throws ServiceException;
}
