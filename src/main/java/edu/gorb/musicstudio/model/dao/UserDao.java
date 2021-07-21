package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    List<User> findUsersByStatus(UserStatus status) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    void updateUserStatus(long userId, UserStatus userStatus) throws DaoException;
}
