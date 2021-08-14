package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    /**
     * Updates course
     *
     * @param course course entity to be updated
     */
    void updateCourse(Course course) throws ServiceException;

    /**
     * Finds all courses
     *
     * @return list of {@link Course} or empty list if no entities found
     */
    List<Course> findAllCourses() throws ServiceException;

    /**
     * Finds courses for page for request
     *
     * @param pageNumber      number of page
     * @param searchParameter search parameter
     * @return list of {@link Course} or empty list if no entities found
     */
    List<Course> findCoursesForRequest(int pageNumber, String searchParameter)
            throws ServiceException;

    /**
     * Calculates pages count by courses count
     *
     * @param coursesCount courses count
     * @return pages count
     */
    int calcPagesCount(int coursesCount);

    /**
     * Counts courses for request
     *
     * @param searchParameter search line
     * @return courses count
     */
    int countCoursesForRequest(String searchParameter) throws ServiceException;

    /**
     * Trims courses descriptions for preview
     *
     * @param courses courses to be trimmed
     * @return courses with trimmed descriptions
     */
    List<Course> trimCoursesDescriptionForPreview(List<Course> courses);

    /**
     * Finds course by id
     *
     * @param courseId course ud
     * @return optional of {@link Course}
     */
    Optional<Course> findCourseById(long courseId) throws ServiceException;

    /**
     * Finds active courses by teacher id
     *
     * @param teacherId teacher id
     * @return list of {@link Course} or empty list if no entities found
     */
    List<Course> findActiveCoursesByTeacherId(long teacherId) throws ServiceException;

    /**
     * Saves new course
     *
     * @param name        course name
     * @param description course description
     * @param price       course price
     * @param imageParts  course image parts
     */
    void saveNewCourse(String name, String description, BigDecimal price, List<Part> imageParts) throws ServiceException;

    /**
     * Updates course
     *
     * @param courseId    course id
     * @param name        course name
     * @param description course description
     * @param price       course price
     * @param isActive    is course active
     * @param imageParts  course image parts
     */
    void updateCourseWithImageUpload(long courseId, String name, String description, BigDecimal price,
                                     boolean isActive, List<Part> imageParts) throws ServiceException;

    /**
     * Adds teacher to course
     *
     * @param courseId  course id
     * @param teacherId teacher id
     */
    void addTeacherToCourse(long courseId, long teacherId) throws ServiceException;

    /**
     * Removes teacher from course
     *
     * @param courseId  course id
     * @param teacherId teacher id
     */
    void removeTeacherFromCourse(long courseId, long teacherId) throws ServiceException;

}
