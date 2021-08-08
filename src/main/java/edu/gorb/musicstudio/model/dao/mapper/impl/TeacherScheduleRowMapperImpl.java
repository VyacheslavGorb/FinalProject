package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.model.dao.ColumnName;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class TeacherScheduleRowMapperImpl implements RowMapper<TeacherSchedule> {
    @Override
    public TeacherSchedule mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ColumnName.TEACHER_ID);
        int dayOfWeek = resultSet.getInt(ColumnName.TEACHES_SCHEDULE_DAY_OF_WEEK);
        LocalTime startTime = resultSet.getTime(ColumnName.TEACHES_SCHEDULE_INTERVAL_START).toLocalTime();
        LocalTime endTime = resultSet.getTime(ColumnName.TEACHES_SCHEDULE_INTERVAL_END).toLocalTime();
        return new TeacherSchedule(id, dayOfWeek, startTime, endTime);
    }
}
