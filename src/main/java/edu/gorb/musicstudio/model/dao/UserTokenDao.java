package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.DaoException;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Dao for user_tokens table
 */
public interface UserTokenDao {
    /**
     * Insert user token
     *
     * @param userId    user id
     * @param token     token value
     * @param timestamp token timestamp
     * @return generated token id
     * @throws DaoException is thrown when error while query execution occurs
     */
    long insetUserToken(long userId, String token, LocalDateTime timestamp) throws DaoException;

    /**
     * Find token by token value
     *
     * @param token  token value
     * @param userId user id
     * @return optional of {@link UserToken}
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<UserToken> findTokenByValue(String token, long userId) throws DaoException;
}
