package edu.gorb.musicstudio.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface of row mappers that map {@link ResultSet} to entity
 *
 * @param <T> type of entity
 */
public interface RowMapper<T> {
    /**
     * Maps <code>resultSet</code> to entity
     *
     * @param resultSet result set to be mapped
     * @return entity of type <code>T</code>
     * @throws SQLException is thrown when an error occurs while working with a <code>resultSet</code>
     */
    T mapRow(ResultSet resultSet) throws SQLException;
}
