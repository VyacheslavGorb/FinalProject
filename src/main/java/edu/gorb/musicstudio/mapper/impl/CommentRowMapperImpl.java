package edu.gorb.musicstudio.mapper.impl;

import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static edu.gorb.musicstudio.dao.ColumnName.*;

public class CommentRowMapperImpl implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet resultSet) throws SQLException {
        long commentId = resultSet.getLong(COMMENT_ID);
        long studentId = resultSet.getLong(COMMENT_STUDENT_ID);
        long teacherId = resultSet.getLong(COMMENT_TEACHER_ID);
        String content = resultSet.getString(COMMENT_CONTENT);
        Timestamp dateTime = resultSet.getTimestamp(COMMENT_TIMESTAMP);
        return new Comment(commentId, studentId, teacherId, content, dateTime);
    }
}
