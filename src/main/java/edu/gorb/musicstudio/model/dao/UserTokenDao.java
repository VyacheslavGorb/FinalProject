package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.DaoException;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserTokenDao {
    int insetUserToken(long userId, String token, LocalDateTime timestamp) throws DaoException;

    Optional<UserToken> findTokenByValue(String token, long userId) throws DaoException;
}
