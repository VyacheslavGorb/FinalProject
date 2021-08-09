package edu.gorb.musicstudio.model.dao.mapper;

import edu.gorb.musicstudio.entity.AbstractEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
