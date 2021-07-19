package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.AbstractEntity;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.DatabaseConnectionException;
import edu.gorb.musicstudio.model.dao.mapper.RowMapper;
import edu.gorb.musicstudio.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
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

    public int executeInsert(String query, Object... parameters) throws DaoException{
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(statement, parameters);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
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
