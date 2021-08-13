package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.CourseDao;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.util.DescriptionUtil;
import edu.gorb.musicstudio.util.HtmlEscapeUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LogManager.getLogger();
    private static final int ITEMS_ON_PAGE_COUNT = 2;
    private static final int MIN_PAGE_COUNT = 1;
    private static final String PICTURE_PROPERTIES = "properties/picture.properties";
    private static final String BASE_PATH_PROPERTY = "image.path.base";
    private static final String TEACHER_FOLDER_PROPERTY = "image.path.course";
    private static final String DEFAULT_BASE_PATH = "D:/pic/";
    private static final String DEFAULT_COURSE_PATH = "course/";
    private static final String JPEG_EXTENSION = ".jpeg";

    private String basePicturePath;
    private String courseFolderPath;

    public CourseServiceImpl() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream(PICTURE_PROPERTIES));
            basePicturePath = properties.getProperty(BASE_PATH_PROPERTY);
            courseFolderPath = properties.getProperty(TEACHER_FOLDER_PROPERTY);
            if (basePicturePath == null || courseFolderPath == null) {
                basePicturePath = DEFAULT_BASE_PATH;
                courseFolderPath = DEFAULT_COURSE_PATH;
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while reading property file {}. {}", PICTURE_PROPERTIES, e.getMessage());
            basePicturePath = DEFAULT_BASE_PATH;
            courseFolderPath = DEFAULT_COURSE_PATH;
        }
    }

    @Override
    public void updateCourse(Course course) throws ServiceException {
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            course.setName(HtmlEscapeUtil.escape(course.getName()));
            course.setDescription(HtmlEscapeUtil.escape(course.getDescription()));
            courseDao.update(course);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating course");
            throw new ServiceException("Error while updating course", e);
        }
    }

    @Override
    public List<Course> findAllCourses() throws ServiceException {
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            return courseDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting all courses");
            throw new ServiceException("Error while selecting all courses", e);
        }
    }

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

    @Override
    public void saveNewCourse(String name, String description, BigDecimal price, List<Part> imageParts)
            throws ServiceException {
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            String escapedName = HtmlEscapeUtil.escape(name);
            String escapedDescription = HtmlEscapeUtil.escape(description);
            Course course = new Course(0, escapedName, escapedDescription, "", price, false);
            long courseId = courseDao.insert(course);
            String relativeImagePath = courseFolderPath + courseId + JPEG_EXTENSION;
            savePicture(relativeImagePath, imageParts);
            course.setActive(true);
            course.setId(courseId);
            course.setPicturePath(relativeImagePath);
            courseDao.update(course);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while saving new course {}", e.getMessage());
            throw new ServiceException("Error while saving new course", e);
        }
    }

    @Override
    public void updateCourseWithImageUpload(long courseId, String name, String description,
                                            BigDecimal price, boolean isActive, List<Part> imageParts)
            throws ServiceException {
        String escapedName = HtmlEscapeUtil.escape(name);
        String escapedDescription = HtmlEscapeUtil.escape(description);
        String relativeImagePath = courseFolderPath + courseId + JPEG_EXTENSION;
        savePicture(relativeImagePath, imageParts);
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            courseDao.update(new Course(courseId, escapedName, escapedDescription, relativeImagePath, price, isActive));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating course. {}", e.getMessage());
            throw new ServiceException("Error while updating course.", e);
        }
    }

    @Override
    public void addTeacherToCourse(long courseId, long teacherId) throws ServiceException {
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            courseDao.addTeacherToCourse(courseId, teacherId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while adding teacher to course");
            throw new ServiceException("Error while adding teacher to course", e);
        }
    }

    @Override
    public void removeTeacherFromCourse(long courseId, long teacherId) throws ServiceException {
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            courseDao.removeTeacherFromCourse(courseId, teacherId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while removing teacher from course");
            throw new ServiceException("Error while removing teacher from course", e);
        }
    }

    private void savePicture(String relativePath, List<Part> imageParts) throws ServiceException {
        String absolutePath = basePicturePath + relativePath;
        try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath)) {
            for (Part part : imageParts) {
                part.getInputStream().transferTo(fileOutputStream);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while saving image. {}", e.getMessage());
            throw new ServiceException("Error while saving image. " + e.getMessage(), e);
        }
    }
}