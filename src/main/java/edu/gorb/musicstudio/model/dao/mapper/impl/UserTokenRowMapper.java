package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.model.dao.ColumnName;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserTokenRowMapper implements RowMapper<UserToken> {
    @Override
    public UserToken mapRow(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong(ColumnName.TOKEN_USER_ID);
        long tokenId = resultSet.getLong(ColumnName.TOKEN_ID);
        String token = resultSet.getString(ColumnName.TOKEN);
        LocalDateTime timestamp = resultSet.getTimestamp(ColumnName.TOKEN_TIMESTAMP).toLocalDateTime();
        return new UserToken(tokenId, userId, token, timestamp);
    }
}
