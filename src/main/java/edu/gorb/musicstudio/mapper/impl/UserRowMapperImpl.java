package edu.gorb.musicstudio.mapper.impl;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static edu.gorb.musicstudio.dao.ColumnName.*;

public class UserRowMapperImpl implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(USER_ID);
        String login = resultSet.getString(USER_LOGIN);
        String password = resultSet.getString(USER_PASSWORD);
        String name = resultSet.getString(USER_NAME);
        String surname = resultSet.getString(USER_SURNAME);
        String patronymic = resultSet.getString(USER_PATRONYMIC);
        String email = resultSet.getString(USER_EMAIL);
        UserRole role = UserRole.valueOf(resultSet.getString(USER_ROLE));
        UserStatus status = UserStatus.valueOf(resultSet.getString(USER_STATUS));
        return new User.Builder()
                .setUserId(id)
                .setLogin(login)
                .setPassword(password)
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic)
                .setEmail(email)
                .setRole(role)
                .setStatus(status)
                .build();
    }
}
