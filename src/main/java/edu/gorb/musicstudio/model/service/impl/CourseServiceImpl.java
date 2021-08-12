package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.CourseDao;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.util.DescriptionUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LogManager.getLogger();
    private static final int ITEMS_ON_PAGE_COUNT = 2;
    private static final int MIN_PAGE_COUNT = 1;

    @Override
    public List<Course> findCoursesForRequest(int pageNumber, String searchParameter) throws ServiceException {
        List<Course> courses;
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        int skipAmount = (pageNumber - 1) * ITEMS_ON_PAGE_COUNT;
        try {
            if (searchParameter == null) {
                courses = courseDao.selectActiveCoursesForPage(skipAmount, ITEMS_ON_PAGE_COUNT);
            } else {
                courses = courseDao.selectActiveCoursesWithSearchForPage(skipAmount, ITEMS_ON_PAGE_COUNT, searchParameter);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting courses for page");
            throw new ServiceException("Error while selecting courses for page", e);
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
                courseAmount = courseDao.countAllActiveCourses();
            } else {
                courseAmount = courseDao.countActiveCoursesWithSearch(searchParameter);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while counting courses");
            throw new ServiceException("Error while counting courses", e);
        }
        return courseAmount;
    }

    @Override
    public List<Course> trimCoursesDescriptionForPreview(List<Course> courses) {
        return courses.stream()
                .map(course -> {
                    String description = course.getDescription();
                    String shortDescription = DescriptionUtil.trimDescriptionForPreview(description);
                    course.setDescription(shortDescription);
                    return course;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findCourseById(long courseId) throws ServiceException {
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            return courseDao.findEntityById(courseId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for course by id={}", courseId);
            throw new ServiceException("Error while searching for course by id=" + courseId, e);
        }
    }

    @Override
    public List<Course> findActiveCoursesByTeacherId(long teacherId) throws ServiceException {
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            return courseDao.selectActiveCoursesByTeacherId(teacherId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for course by teacher id={}", teacherId);
            throw new ServiceException("Error while searching for course by teacher id=" + teacherId, e);
        }
    }
}