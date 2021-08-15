package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.Comment;
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

public class CommentRowMapperTest {

    @DataProvider(name = "comments")
    public Object[][] createComments() {
        return new Object[][]{
                {new Comment(10, 10, 10, "content1",
                        LocalDateTime.of(10, 10, 10, 10, 10), true)},
                {new Comment(20, 10, 20, "content2",
                        LocalDateTime.of(10, 10, 4, 10, 10), false)}
        };
    }

    @Test(dataProvider = "comments")
    public void mapRowTest(Comment expected) throws SQLException {
        ResultSet resultSet = EasyMock.mock(ResultSet.class);
        EasyMock.expect(resultSet.getLong(COMMENT_ID)).andReturn(expected.getId());
        EasyMock.expect(resultSet.getLong(COMMENT_STUDENT_ID)).andReturn(expected.getStudentId());
        EasyMock.expect(resultSet.getLong(COMMENT_COURSE_ID)).andReturn(expected.getCourseId());
        EasyMock.expect(resultSet.getString(COMMENT_CONTENT)).andReturn(expected.getContent());
        EasyMock.expect(resultSet.getTimestamp(COMMENT_TIMESTAMP)).andReturn(Timestamp.valueOf(expected.getDateTime()));
        EasyMock.expect(resultSet.getBoolean(COMMENT_IS_ACTIVE)).andReturn(expected.isActive());
        EasyMock.replay(resultSet);
        RowMapper<Comment> commentRowMapper = new CommentRowMapperImpl();
        Comment created = commentRowMapper.mapRow(resultSet);
        Assert.assertEquals(created, expected);
    }
}
