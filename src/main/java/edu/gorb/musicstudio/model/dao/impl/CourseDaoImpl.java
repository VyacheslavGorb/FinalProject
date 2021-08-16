package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.DatabaseConnectionException;
import edu.gorb.musicstudio.model.dao.CourseDao;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.mapper.impl.CourseRowMapperImpl;
import edu.gorb.musicstudio.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int FIRST_COLUMN_INDEX = 1;

    private static final String SELECT_ALL_COURSES =
            "SELECT id_course, name, description, picture_path, price_per_hour, is_active\n" +
                    "FROM courses";

    private static final String SELECT_COURSE_BY_ID =
            "SELECT id_course, name, description, picture_path, price_per_hour, is_active\n" +
                    "FROM courses\n" +
                    "WHERE id_course=?";

    private static final String INSERT_COURSE =
            "INSERT INTO courses (name, description, picture_path, price_per_hour, is_active)\n" +
                    "    VALUE (?, ?, ?, ?, ?)";

    private static final String UPDATE_COURSE = "UPDATE courses\n" +
            "SET name=?,\n" +
            "    description=?,\n" +
            "    picture_path=?,\n" +
            "    price_per_hour=?,\n" +
            "    is_active=?\n" +
            "WHERE id_course = ?";

    private static final String UPDATE_COURSE_STATUS =
            "UPDATE courses\n" +
                    "SET is_active=?\n" +
                    "WHERE id_course = ?";

    private static final String SELECT_COURSES_WITH_SEARCH_FOR_PAGE =
            "SELECT id_course, name, description, picture_path, price_per_hour, is_active\n" +
                    "FROM courses\n" +
                    "WHERE name LIKE CONCAT('%', ?, '%') and is_active=1\n" +
                    "LIMIT ?, ?";

    private static final String SELECT_COURSES_FOR_PAGE =
            "SELECT id_course, name, description, picture_path, price_per_hour, is_active\n" +
                    "FROM courses  WHERE is_active=1\n" +
                    "LIMIT ?, ?";

    private static final String COUNT_ALL_COURSES = "SELECT COUNT(name) FROM courses WHERE is_active=1";

    private static final String COUNT_COURSES_WITH_SEARCH =
            "SELECT COUNT(name) FROM courses\n" +
                    "WHERE name LIKE CONCAT('%',?,'%') and is_active=1";

    private static final String SELECT_COURSES_BY_TEACHER_ID =
            "SELECT DISTINCT courses.id_course, name, description, picture_path, price_per_hour, is_active\n" +
                    "FROM courses\n" +
                    "         JOIN teacher_descriptions_has_courses tdhc on courses.id_course = tdhc.id_course\n" +
                    "WHERE is_active = 1 and id_teacher=?";

    private static final String ADD_TEACHER_TO_COURSE =
            "INSERT INTO teacher_descriptions_has_courses (id_teacher, id_course) VALUE (?, ?)";

    private static final String REMOVE_TEACHER_FROM_COURSE =
            "DELETE\n" +
                    "FROM teacher_descriptions_has_courses\n" +
                    "WHERE id_teacher = ?\n" +
                    "  and id_course = ?";

    private final JdbcHelper<Course> jdbcHelper;

    public CourseDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new CourseRowMapperImpl());
    }

    @Override
    public List<Course> findAll() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_COURSES);
    }

    @Override
    public Optional<Course> findEntityById(long id) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_COURSE_BY_ID, id);
    }

    @Override
    public long insert(Course course) throws DaoException {
        return jdbcHelper.executeInsert(INSERT_COURSE,
                course.getName(),
                course.getDescription(),
                course.getPicturePath(),
                course.getPricePerHour(),
                course.isActive());
    }

    @Override
    public void update(Course course) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_COURSE,
                course.getName(),
                course.getDescription(),
                course.getPicturePath(),
                course.getPricePerHour(),
                course.isActive(),
                course.getId());
    }

    @Override
    public List<Course> selectActiveCoursesWithSearchForPage(int skipAmount, int coursePerPageAmount, String search)
            throws DaoException {
        return jdbcHelper.executeQuery(SELECT_COURSES_WITH_SEARCH_FOR_PAGE, search, skipAmount, coursePerPageAmount);
    }

    @Override
    public List<Course> selectActiveCoursesForPage(int skipAmount, int coursePerPageAmount) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_COURSES_FOR_PAGE, skipAmount, coursePerPageAmount);
    }

    @Override
    public int countAllActiveCourses() throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(COUNT_ALL_COURSES);
            resultSet.next();
            result = resultSet.getInt(FIRST_COLUMN_INDEX);
        } catch (SQLException | DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public int countActiveCoursesWithSearch(String searchParameter) throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_COURSES_WITH_SEARCH)) {
            statement.setString(FIRST_COLUMN_INDEX, searchParameter);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(FIRST_COLUMN_INDEX);
        } catch (SQLException | DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Course> selectActiveCoursesByTeacherId(long teacherId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_COURSES_BY_TEACHER_ID, teacherId);
    }

    @Override
    public void addTeacherToCourse(long courseId, long teacherId) throws DaoException {
        jdbcHelper.executeUpdate(ADD_TEACHER_TO_COURSE, teacherId, courseId);
    }

    @Override
    public void removeTeacherFromCourse(long courseId, long teacherId) throws DaoException {
        jdbcHelper.executeUpdate(REMOVE_TEACHER_FROM_COURSE, teacherId, courseId);
    }

    @Override
    public void updateStatus(long courseId, boolean isActive) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_COURSE_STATUS, isActive, courseId);
    }
}
