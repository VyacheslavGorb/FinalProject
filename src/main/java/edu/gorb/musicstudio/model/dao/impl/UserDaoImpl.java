package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.DatabaseConnectionException;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.UserDao;
import edu.gorb.musicstudio.model.dao.mapper.impl.UserRowMapperImpl;
import edu.gorb.musicstudio.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_USERS = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       user_role,\n" +
            "       user_status\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role ORDER BY name";

    private static final String SELECT_ALL_ACTIVE_TEACHERS =
            "SELECT id_user,\n" +
                    "       login,\n" +
                    "       password_hash,\n" +
                    "       name,\n" +
                    "       surname,\n" +
                    "       patronymic,\n" +
                    "       email,\n" +
                    "       user_role,\n" +
                    "       user_status\n" +
                    "FROM users\n" +
                    "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
                    "         JOIN user_roles ur on ur.id_user_role = users.id_user_role WHERE user_role='TEACHER' and user_status='ACTIVE' ORDER BY name";

    private static final String SELECT_USER_BY_ID = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       user_role,\n" +
            "       user_status\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
            "WHERE id_user=?";


    private static final String SELECT_USER_BY_LOGIN = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       user_status,\n" +
            "       user_role\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
            "WHERE login=?";


    private static final String SELECT_USER_BY_EMAIL = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       user_status,\n" +
            "       user_role\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
            "WHERE email=?";

    private static final String INSERT_NEW_USER =
            "INSERT INTO users (login, password_hash, name, surname, patronymic, email, id_user_status,\n" +
                    "             id_user_role) VALUE (?, ?, ?, ?, ?, ?,\n" +
                    "                   (SELECT us.id_user_status FROM user_statuses us where us.user_status = ?),\n" +
                    "                   (SELECT ur.id_user_role FROM user_roles ur where ur.user_role = ?))";

    private static final String UPDATE_USER = "UPDATE users\n" +
            "SET password_hash  = ?,\n" +
            "    name           =?,\n" +
            "    surname        = ?,\n" +
            "    patronymic     = ?,\n" +
            "    id_user_status = (SELECT ur.id_user_role FROM user_roles ur WHERE ur.user_role = ?),\n" +
            "    id_user_role   = (SELECT us.id_user_status FROM user_statuses us WHERE us.user_status = ?)\n" +
            "WHERE id_user = ?";

    private static final String UPDATE_USER_STATUS = "UPDATE users\n" +
            "SET id_user_status = (SELECT us.id_user_status FROM user_statuses us WHERE us.user_status = ?)\n" +
            "WHERE id_user = ?";

    private static final String SELECT_ACTIVE_TEACHERS_FOR_PAGE =
            "SELECT id_user,\n" +
                    "       login,\n" +
                    "       password_hash,\n" +
                    "       name,\n" +
                    "       surname,\n" +
                    "       patronymic,\n" +
                    "       email,\n" +
                    "       user_status,\n" +
                    "       user_role\n" +
                    "FROM users\n" +
                    "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
                    "         JOIN user_statuses us on users.id_user_status = us.id_user_status\n" +
                    "WHERE user_role = 'TEACHER' and user_status = 'ACTIVE' ORDER BY name\n" +
                    "LIMIT ?, ?";

    private static final String SELECT_ACTIVE_TEACHERS_FOR_PAGE_WITH_SEARCH =
            "SELECT id_user,\n" +
                    "       login,\n" +
                    "       password_hash,\n" +
                    "       name,\n" +
                    "       surname,\n" +
                    "       patronymic,\n" +
                    "       email,\n" +
                    "       user_status,\n" +
                    "       user_role\n" +
                    "FROM users\n" +
                    "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
                    "         JOIN user_statuses us on users.id_user_status = us.id_user_status\n" +
                    "WHERE user_role = 'TEACHER' and user_status = 'ACTIVE'\n" +
                    "  and CONCAT(name, ' ', surname, ' ', patronymic) LIKE CONCAT('%', ?, '%') ORDER BY name\n" +
                    "LIMIT ?, ?";

    private static final String COUNT_ACTIVE_TEACHERS =
            "SELECT COUNT(id_user)\n" +
                    "FROM users\n" +
                    "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
                    "WHERE user_role = 'TEACHER' and id_user_status = (SELECT user_statuses.id_user_status FROM user_statuses WHERE user_statuses.user_status = 'ACTIVE')";

    private static final String COUNT_ACTIVE_TEACHERS_WITH_SEARCH =
            "SELECT COUNT(id_user)\n" +
                    "FROM users\n" +
                    "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
                    "WHERE user_role = 'TEACHER' and id_user_status = (SELECT user_statuses.id_user_status FROM user_statuses WHERE user_statuses.user_status = 'ACTIVE')\n" +
                    "  and CONCAT(name, ' ', surname, ' ', patronymic) LIKE CONCAT('%', ?, '%')";

    private static final String SELECT_TEACHER_FOR_COURSE =
            "SELECT id_user,\n" +
                    "       login,\n" +
                    "       password_hash,\n" +
                    "       name,\n" +
                    "       surname,\n" +
                    "       patronymic,\n" +
                    "       email,\n" +
                    "       user_status,\n" +
                    "       user_role\n" +
                    "FROM users\n" +
                    "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
                    "         JOIN user_statuses us on users.id_user_status = us.id_user_status\n" +
                    "         JOIN teacher_descriptions_has_courses on id_teacher = id_user\n" +
                    "WHERE user_status = 'ACTIVE' \n" +
                    "  and id_course = ? ORDER BY name";

    private static final int FIRST_COLUMN_INDEX = 1;

    private final JdbcHelper<User> jdbcHelper;

    public UserDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new UserRowMapperImpl());
    }

    @Override
    public List<User> findAll() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_USERS);
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_USER_BY_ID, id);
    }

    @Override
    public long insert(User user) throws DaoException {
        return jdbcHelper.executeInsert(INSERT_NEW_USER,
                user.getLogin(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getEmail(),
                user.getStatus().toString(),
                user.getRole().toString());
    }

    @Override
    public void update(User user) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_USER,
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getStatus().toString(),
                user.getRole().toString());
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_USER_BY_LOGIN, login);
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_USER_BY_EMAIL, email);
    }

    @Override
    public void updateUserStatus(long userId, UserStatus userStatus) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_USER_STATUS, userStatus.toString(), userId);
    }

    @Override
    public List<User> selectActiveTeachersForPage(int skipAmount, int teachersPerPageAmount) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ACTIVE_TEACHERS_FOR_PAGE, skipAmount, teachersPerPageAmount);
    }

    @Override
    public List<User> selectActiveTeachersWithSearchForPage(int skipAmount, int teacherPerPageAmount, String search) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ACTIVE_TEACHERS_FOR_PAGE_WITH_SEARCH, search, skipAmount, teacherPerPageAmount);
    }

    @Override
    public int countActiveTeachers() throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(COUNT_ACTIVE_TEACHERS);
            resultSet.next();
            result = resultSet.getInt(FIRST_COLUMN_INDEX);
        } catch (SQLException | DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return result;
    }


    @Override
    public int countActiveTeachersWithSearch(String searchParameter) throws DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ACTIVE_TEACHERS_WITH_SEARCH)) {
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
    public List<User> selectTeachersForCourse(long courseId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_TEACHER_FOR_COURSE, courseId);
    }

    @Override
    public List<User> findAllActiveTeachers() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_ACTIVE_TEACHERS);
    }
}
