package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.model.dao.ColumnName;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapperImpl implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ColumnName.COURSE_COURSE_ID);
        String name = resultSet.getString(ColumnName.COURSE_NAME);
        String description = resultSet.getString(ColumnName.COURSE_DESCRIPTION);
        String picturePath = resultSet.getString(ColumnName.COURSE_PICTURE_PATH);
        boolean isActive = resultSet.getBoolean(ColumnName.COURSE_IS_ACTIVE);
        BigDecimal pricePerHour = resultSet.getBigDecimal(ColumnName.COURSE_PRICE_PER_HOUR);
        return new Course(id, name, description, picturePath, pricePerHour, isActive);
    }
}
