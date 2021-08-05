package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.CourseDao;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.service.CourseService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LogManager.getLogger();
    private static final int ITEMS_ON_PAGE_COUNT = 1; //TODO change to 5
    private static final int MIN_PAGE_COUNT = 1;

    @Override
    public List<Course> findCoursesForRequest(int pageNumber, String searchParameter) throws ServiceException {
        List<Course> courses;
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        int skipAmount = (pageNumber - 1) * ITEMS_ON_PAGE_COUNT;
        try {
            if (searchParameter == null) {
                courses = courseDao.selectCoursesForPage(skipAmount, ITEMS_ON_PAGE_COUNT);
            } else {
                courses = courseDao.selectCoursesWithSearchForPage(skipAmount, ITEMS_ON_PAGE_COUNT, searchParameter);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting courses for page");
            throw new ServiceException(e);
        }
        return courses;
    }

    @Override
    public int calcPagesCount(int coursesCount) {
        int result = (int) Math.ceil((double) coursesCount / ITEMS_ON_PAGE_COUNT);
        return result != 0 ? result : MIN_PAGE_COUNT;
    }

    @Override
    public int countCoursesForRequest(String searchParameter) throws ServiceException {
        int courseAmount;
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            if (searchParameter == null) {
                courseAmount = courseDao.countAllCourses();
            } else {
                courseAmount = courseDao.countCoursesWithSearch(searchParameter);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while counting courses");
            throw new ServiceException(e);
        }
        return courseAmount;
    }
}
//TODO logs (file)