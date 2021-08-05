package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.DatabaseConnectionException;
import edu.gorb.musicstudio.model.dao.CourseDao;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.mapper.impl.CourseRowMapper;
import edu.gorb.musicstudio.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {
    private static final Logger logger = LogManager.getLogger();

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
            "    is_active=?,\n" +
            "WHERE id_course = ?";

    private static final String SELECT_COURSES_WITH_SEARCH_FOR_PAGE =
            "SELECT id_course, name, description, picture_path, price_per_hour, is_active\n" +
                    "FROM courses\n" +
                    "WHERE name LIKE CONCAT('%', ?, '%')\n" +
                    "LIMIT ?, ?";

    private static final String SELECT_COURSES_FOR_PAGE =
            "SELECT id_course, name, description, picture_path, price_per_hour, is_active\n" +
                    "FROM courses\n" +
                    "LIMIT ?, ?";

    private static final String COUNT_ALL_COURSES = "SELECT COUNT(name) FROM courses";

    private static final String COUNT_COURSES_WITH_SEARCH =
            "SELECT COUNT(name) FROM courses\n" +
                    "WHERE name LIKE CONCAT('%',?,'%')";

    private final JdbcHelper<Course> jdbcHelper;

    public CourseDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new CourseRowMapper());
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
    public int insert(Course course) throws DaoException {
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
    public List<Course> selectCoursesWithSearchForPage(int skipAmount, int coursePerPageAmount, String search)
            throws DaoException {
        return jdbcHelper.executeQuery(SELECT_COURSES_WITH_SEARCH_FOR_PAGE, search, skipAmount, coursePerPageAmount);
    }

    @Override
    public List<Course> selectCoursesForPage(int skipAmount, int coursePerPageAmount) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_COURSES_FOR_PAGE, skipAmount, coursePerPageAmount);
    }

    @Override
    public int countAllCourses() throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(COUNT_ALL_COURSES);
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException | DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public int countCoursesWithSearch(String searchParameter) throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_COURSES_WITH_SEARCH)) {
            statement.setString(1, searchParameter);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException | DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return result;
    }


}
