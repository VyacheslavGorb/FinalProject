package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static edu.gorb.musicstudio.model.dao.ColumnName.*;

public class UserTokenRowMapperTest {
    @DataProvider(name = "tokens")
    public Object[][] createComments() {
        return new Object[][]{
                {new UserToken(10L, 10L, "content1",
                        LocalDateTime.of(10, 10, 10, 10, 10))},
                {new UserToken(20L, 20L, "content2",
                        LocalDateTime.of(10, 10, 10, 10, 10))},
        };
    }

    @Test(dataProvider = "tokens")
    public void mapRowTest(UserToken expected) throws SQLException {
        ResultSet resultSet = EasyMock.mock(ResultSet.class);
        EasyMock.expect(resultSet.getLong(TOKEN_ID)).andReturn(expected.getId());
        EasyMock.expect(resultSet.getLong(TOKEN_USER_ID)).andReturn(expected.getUserId());
        EasyMock.expect(resultSet.getString(TOKEN)).andReturn(expected.getToken());
        EasyMock.expect(resultSet.getTimestamp(TOKEN_TIMESTAMP)).andReturn(Timestamp.valueOf(expected.getCreationTimestamp()));
        EasyMock.replay(resultSet);
        RowMapper<UserToken> userTokenRowMapper = new UserTokenRowMapperImpl();
        UserToken created = userTokenRowMapper.mapRow(resultSet);
        Assert.assertEquals(created, expected);
    }
}
