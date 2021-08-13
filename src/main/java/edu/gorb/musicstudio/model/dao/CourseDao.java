package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;

public interface CourseDao extends BaseDao<Course> {
    List<Course> selectActiveCoursesWithSearchForPage(int skipAmount, int coursePerPageAmount, String search)
            throws DaoException;

    List<Course> selectActiveCoursesForPage(int skipAmount, int coursePerPageAmount) throws DaoException;

    int countAllActiveCourses() throws DaoException;

    int countActiveCoursesWithSearch(String searchParameter) throws DaoException;

    List<Course> selectActiveCoursesByTeacherId(long teacherId) throws DaoException;

    void addTeacherToCourse(long courseId, long teacherId) throws DaoException;

    void removeTeacherFromCourse(long courseId, long teacherId) throws DaoException;
}
