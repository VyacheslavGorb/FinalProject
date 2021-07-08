package edu.gorb.musicstudio.dao;

import edu.gorb.musicstudio.entity.AbstractEntity;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcHelper<T extends AbstractEntity> {
    private Connection connection;
    private RowMapper<T> mapper;

    public JdbcHelper(Connection connection, RowMapper<T> mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    public List<T> executeQuery(String query, Object... parameters) throws DaoException {
        List<T> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= parameters.length; i++) {
                statement.setObject(i, parameters[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = mapper.mapRow(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    public Optional<T> executeQueryForSingleResult(String query, Object... parameters) throws DaoException {
        List<T> entities = executeQuery(query, parameters);
        return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(1));
    }

    public void executeUpdate(String query, Object... parameters) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= parameters.length; i++) {
                statement.setObject(i, parameters[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
