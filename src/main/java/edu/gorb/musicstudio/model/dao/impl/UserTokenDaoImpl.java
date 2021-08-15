package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.UserTokenDao;
import edu.gorb.musicstudio.model.dao.mapper.impl.UserTokenRowMapperImpl;
import edu.gorb.musicstudio.model.pool.ConnectionPool;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserTokenDaoImpl implements UserTokenDao {

    private static final String INSERT_USER_TOKEN =
            "INSERT INTO user_tokens (id_user, token, timestamp) VALUE (?, ?, ?)";

    private static final String FIND_USER_TOKEN = "SELECT id_token, id_user, token, timestamp\n" +
            "FROM user_tokens\n" +
            "WHERE id_user = ?\n" +
            "  and token = ?\n" +
            "ORDER BY timestamp DESC\n" +
            "LIMIT 1";

    private final JdbcHelper<UserToken> jdbcHelper;

    public UserTokenDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new UserTokenRowMapperImpl());
    }

    @Override
    public long insetUserToken(long userId, String token, LocalDateTime timestamp) throws DaoException {
        return jdbcHelper.executeInsert(INSERT_USER_TOKEN, userId, token, Timestamp.valueOf(timestamp));
    }

    @Override
    public Optional<UserToken> findTokenByValue(String token, long userId) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(FIND_USER_TOKEN, userId, token);
    }
}
