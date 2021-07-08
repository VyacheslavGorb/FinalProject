package edu.gorb.musicstudio.dao.impl;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.mapper.impl.UserRowMapperImpl;
import edu.gorb.musicstudio.dao.JdbcHelper;
import edu.gorb.musicstudio.dao.UserDao;
import edu.gorb.musicstudio.pool.ConnectionPool;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final String SELECT_ALL_USERS = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       phone_number,\n" +
            "       user_role,\n" +
            "       user_status\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role";

    private static final String SELECT_USER_BY_ID = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       phone_number,\n" +
            "       user_role,\n" +
            "       user_status\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
            "WHERE id_user=?";

    private static final String SELECT_USERS_BY_STATUS = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       phone_number,\n" +
            "       user_status,\n" +
            "       user_role\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
            "WHERE user_status=?";

    private static final String SELECT_USER_BY_LOGIN = "SELECT id_user,\n" +
            "       login,\n" +
            "       password_hash,\n" +
            "       name,\n" +
            "       surname,\n" +
            "       patronymic,\n" +
            "       email,\n" +
            "       phone_number,\n" +
            "       user_status,\n" +
            "       user_role\n" +
            "FROM users\n" +
            "         JOIN user_statuses us on us.id_user_status = users.id_user_status\n" +
            "         JOIN user_roles ur on ur.id_user_role = users.id_user_role\n" +
            "WHERE login=?";

    private static final String INSERT_NEW_USER =
            "INSERT INTO users (login, password_hash, name, surname, patronymic, email, phone_number, id_user_status,\n" +
                    "             id_user_role) VALUE (?, ?, ?, ?, ?, ?, ?,\n" +
                    "                   (SELECT us.id_user_status FROM user_statuses us where us.user_status = ?),\n" +
                    "                   (SELECT ur.id_user_role FROM user_roles ur where ur.user_role = ?))";

    private static final String UPDATE_USER = "UPDATE users\n" +
            "SET password_hash  = ?,\n" +
            "    name           =?,\n" +
            "    surname        = ?,\n" +
            "    patronymic     = ?,\n" +
            "    phone_number   = ?,\n" +
            "    id_user_status = (SELECT ur.id_user_role FROM user_roles ur WHERE ur.user_role = ?),\n" +
            "    id_user_role   = (SELECT us.id_user_status FROM user_statuses us WHERE us.user_status = ?)\n" +
            "WHERE id_user = ?";

    private JdbcHelper<User> jdbcHelper;

    public UserDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new UserRowMapperImpl());
    }

    @Override
    public List<User> findAll() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_USERS);
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_USER_BY_ID, id);
    }

    @Override
    public void insert(User user) throws DaoException {
        jdbcHelper.executeUpdate(INSERT_NEW_USER,
                user.getLogin(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPhoneNumber(),
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
                user.getPhoneNumber(),
                user.getStatus().toString(),
                user.getRole().toString());
    }

    @Override
    public List<User> selectUserByStatus(UserStatus status) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_USERS_BY_STATUS, status.toString());
    }

    @Override
    public Optional<User> selectUserByLogin(String login) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_USER_BY_LOGIN, login);
    }
}
