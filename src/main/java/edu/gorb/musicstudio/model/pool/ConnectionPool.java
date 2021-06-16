package edu.gorb.musicstudio.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static AtomicBoolean isInitialised = new AtomicBoolean(false);
    private static final int DEFAULT_POOL_SIZE = 32;
    private BlockingDeque<ProxyConnection> freeConnections;
    private BlockingDeque<ProxyConnection> givenAwayConnections;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>();
        givenAwayConnections = new LinkedBlockingDeque<>();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException throwables) {
                logger.warn("Error while creating connection");
            }

            if (freeConnections.isEmpty()) {
                logger.fatal("Unable to create connections");
                throw new RuntimeException("Unable to create connections");
            }
        }
    }

    public static ConnectionPool getInstance() {
        while (instance == null) {
            if (isInitialised.compareAndSet(false, true)) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while getting connection: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() != ProxyConnection.class) {
            logger.log(Level.ERROR, "Illegal connection type");
            return; // TODO????? or throw
        }
        try {
            givenAwayConnections.remove(connection);
            freeConnections.put((ProxyConnection) connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while releasing connection: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException | InterruptedException e) {
                logger.log(Level.ERROR, "Connection is not destroyed: {}", e.getMessage());
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error while driver deregistration: {}", e.getMessage());
            }
        });
    }


}
