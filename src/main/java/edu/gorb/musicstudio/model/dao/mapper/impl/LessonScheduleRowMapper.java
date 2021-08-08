package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.model.dao.ColumnName;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LessonScheduleRowMapper implements RowMapper<LessonSchedule> {
    @Override
    public LessonSchedule mapRow(ResultSet resultSet) throws SQLException {
        long scheduleId = resultSet.getLong(ColumnName.LESSON_ID);
        long studentId = resultSet.getLong(ColumnName.LESSON_STUDENT_ID);
        long teacherId = resultSet.getLong(ColumnName.LESSON_TEACHER_ID);
        long courseId = resultSet.getLong(ColumnName.LESSON_COURSE_ID);
        LocalDateTime startDateTime = resultSet.getTimestamp(ColumnName.LESSON_TIMESTAMP).toLocalDateTime();
        LocalTime duration = resultSet.getTime(ColumnName.LESSON_DURATION).toLocalTime();
        LessonSchedule.LessonStatus status =
                LessonSchedule.LessonStatus.valueOf(resultSet.getString((ColumnName.LESSON_STATUS)));
        return new LessonSchedule(scheduleId, studentId, teacherId, courseId, startDateTime, duration, status);
    }
}
