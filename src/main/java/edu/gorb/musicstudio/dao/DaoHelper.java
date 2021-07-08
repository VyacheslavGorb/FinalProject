package edu.gorb.musicstudio.dao;

import edu.gorb.musicstudio.dao.impl.CommentDaoImpl;
import edu.gorb.musicstudio.dao.impl.UserDaoImpl;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.DatabaseConnectionException;
import edu.gorb.musicstudio.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {
    private Connection connection;

    public DaoHelper() throws DaoException {
        try {
            this.connection = ConnectionPool.getInstance().getConnection();
        } catch (DatabaseConnectionException e) {
            throw new DaoException(e);
        }
    }

    public UserDao createUserDao() {
        return new UserDaoImpl(connection);
    }

    public CommentDao createCommentDao() {
        return new CommentDaoImpl(connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
