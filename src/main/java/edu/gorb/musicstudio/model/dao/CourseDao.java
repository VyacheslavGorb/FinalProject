package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;

/**
 * Dao for courses table
 */
public interface CourseDao extends BaseDao<Course> {
    /**
     * Finds active courses for page with search
     *
     * @param skipAmount          entity amount to be skipped
     * @param coursePerPageAmount course amount displayed on each page
     * @param search              search line
     * @return list of {@link Course} entities or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Course> selectActiveCoursesWithSearchForPage(int skipAmount, int coursePerPageAmount, String search)
            throws DaoException;

    /**
     * Finds active courses for pag
     *
     * @param skipAmount          entity amount to be skipped
     * @param coursePerPageAmount course amount displayed on each page
     * @return list of {@link Course} entities or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Course> selectActiveCoursesForPage(int skipAmount, int coursePerPageAmount) throws DaoException;

    /**
     * Counts all active courses
     *
     * @return active course count
     * @throws DaoException is thrown when error while query execution occurs
     */
    int countAllActiveCourses() throws DaoException;

    /**
     * Counts all active courses for search parameter
     *
     * @param searchParameter search line
     * @return active course count for search line
     * @throws DaoException is thrown when error while query execution occurs
     */
    int countActiveCoursesWithSearch(String searchParameter) throws DaoException;

    /**
     * Finds active courses by teacher id
     *
     * @param teacherId teacher id
     * @return list of {@link Course} entities or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Course> selectActiveCoursesByTeacherId(long teacherId) throws DaoException;

    /**
     * Adds teacher to course
     *
     * @param courseId  course id
     * @param teacherId teacher id
     * @throws DaoException is thrown when error while query execution occurs
     */
    void addTeacherToCourse(long courseId, long teacherId) throws DaoException;

    /**
     * Removes teacher from course
     *
     * @param courseId  course id
     * @param teacherId teacher id
     * @throws DaoException is thrown when error while query execution occurs
     */
    void removeTeacherFromCourse(long courseId, long teacherId) throws DaoException;

    /**
     * Updates course status
     *
     * @param courseId id of course to be updated
     * @param isActive new status
     */
    void updateStatus(long courseId, boolean isActive) throws DaoException;
}
