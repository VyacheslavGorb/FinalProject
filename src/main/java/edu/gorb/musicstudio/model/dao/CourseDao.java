package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;

public interface CourseDao extends BaseDao<Course> {
    List<Course> selectCoursesWithSearchForPage(int skipAmount, int coursePerPageAmount, String search) throws DaoException;

    List<Course> selectCoursesForPage(int skipAmount, int coursePerPageAmount) throws DaoException;

    int countAllCourses() throws DaoException;

    int countCoursesWithSearch(String searchParameter) throws DaoException;
}
