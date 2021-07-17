package edu.gorb.musicstudio.dao;

import edu.gorb.musicstudio.entity.AbstractEntity;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.DatabaseConnectionException;
import edu.gorb.musicstudio.mapper.RowMapper;
import edu.gorb.musicstudio.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcHelper<T extends AbstractEntity> {
    private static final Logger logger = LogManager.getLogger();
    private ConnectionPool connectionPool;
    private RowMapper<T> mapper;

    public JdbcHelper(ConnectionPool connectionPool, RowMapper<T> mapper) {
        this.connectionPool = connectionPool;
        this.mapper = mapper;
    }

    public List<T> executeQuery(String query, Object... parameters) throws DaoException {
        List<T> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            fillPreparedStatement(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = mapper.mapRow(resultSet);
                result.add(entity);
            }
        } catch (SQLException | DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return result;
    }

    public Optional<T> executeQueryForSingleResult(String query, Object... parameters) throws DaoException {
        List<T> entities = executeQuery(query, parameters);
        return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(0));
    }

    public void executeUpdate(String query, Object... parameters) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            fillPreparedStatement(statement, parameters);
            statement.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
    }

    private void fillPreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i-1]);
        }
    }
}
