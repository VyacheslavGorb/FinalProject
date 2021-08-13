package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    void updateCourse(Course course) throws ServiceException;

    List<Course> findAllCourses() throws ServiceException;

    List<Course> findCoursesForRequest(int pageNumber, String searchParameter)
            throws ServiceException;

    int calcPagesCount(int coursesCount);

    int countCoursesForRequest(String searchParameter) throws ServiceException;

    List<Course> trimCoursesDescriptionForPreview(List<Course> courses);

    Optional<Course> findCourseById(long courseId) throws ServiceException;

    List<Course> findActiveCoursesByTeacherId(long teacherId) throws ServiceException;

    void saveNewCourse(String name, String description, BigDecimal price, List<Part> imageParts) throws ServiceException;

    void updateCourseWithImageUpload(long courseId, String name, String description, BigDecimal price,
                                     boolean isActive, List<Part> imageParts) throws ServiceException;

    void addTeacherToCourse(long courseId, long teacherId) throws ServiceException;

    void removeTeacherFromCourse(long courseId, long teacherId) throws ServiceException;

}
