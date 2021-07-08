package edu.gorb.musicstudio.mapper;

import edu.gorb.musicstudio.entity.AbstractEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends AbstractEntity> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
