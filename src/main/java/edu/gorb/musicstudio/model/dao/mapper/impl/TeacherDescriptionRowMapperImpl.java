package edu.gorb.musicstudio.model.dao.mapper.impl;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.model.dao.ColumnName;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDescriptionRowMapperImpl implements RowMapper<TeacherDescription> {

    @Override
    public TeacherDescription mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ColumnName.TEACHER_ID);
        String description = resultSet.getString(ColumnName.TEACHER_SELF_DESCRIPTION);
        int experience = resultSet.getInt(ColumnName.TEACHER_EXPERIENCE);
        String picturePath = resultSet.getString(ColumnName.TEACHER_PICTURE_PATH);
        return new TeacherDescription(id, description, experience, picturePath);
    }
}
