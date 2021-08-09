package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static edu.gorb.musicstudio.model.dao.ColumnName.*;

public class CommentRowMapperImpl implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet resultSet) throws SQLException {
        long commentId = resultSet.getLong(COMMENT_ID);
        long studentId = resultSet.getLong(COMMENT_STUDENT_ID);
        long courseId = resultSet.getLong(COMMENT_COURSE_ID);
        String content = resultSet.getString(COMMENT_CONTENT);
        LocalDateTime dateTime = resultSet.getTimestamp(COMMENT_TIMESTAMP).toLocalDateTime();
        return new Comment(commentId, studentId, courseId, content, dateTime);
    }
}
