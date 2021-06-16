package edu.gorb.musicstudio.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static AtomicBoolean isInitialised = new AtomicBoolean(false);
    private static final int DEFAULT_POOL_SIZE = 32;
    private Deque<ProxyConnection> freeConnections;
    private Deque<ProxyConnection> givenAwayConnections;
    private Lock connectionLock;
    private Condition freeConnectionCondition;

    private ConnectionPool() {
        connectionLock = new ReentrantLock(true);
        freeConnectionCondition = connectionLock.newCondition();
        freeConnections = new ArrayDeque<>();
        givenAwayConnections = new ArrayDeque<>();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException e) {
                logger.log(Level.WARN, "Error while creating connection: {}", e.getMessage());
            }

            if (freeConnections.isEmpty()) {
                logger.fatal("Unable to create connections");
                throw new RuntimeException("Unable to create connections");
            }

            setValidationTask();
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
        connectionLock.lock();
        ProxyConnection connection;
        try {
            if (freeConnections.isEmpty()) {
                freeConnectionCondition.await();
            }
            connection = freeConnections.getLast();
            givenAwayConnections.addLast(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread is interrupted" + e.getMessage()); // FIXME how ? | logs
        } finally {
            connectionLock.unlock();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            logger.log(Level.ERROR, "Illegal connection type");
            return; // FIXME  |  or throw
        }
        connectionLock.lock();
        try {
            givenAwayConnections.remove(connection);
            freeConnections.addLast((ProxyConnection) connection);
        } finally {
            freeConnectionCondition.signal();
            connectionLock.unlock();
        }
    }

    public void destroyPool() {
        connectionLock.lock();
        try {
            freeConnections.forEach(ProxyConnection::reallyClose);
            givenAwayConnections.forEach(ProxyConnection::reallyClose);
        } finally {
            connectionLock.unlock();
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


    private void setValidationTask() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                connectionLock.lock();
                try {
                    int connectionAmount = freeConnections.size() + givenAwayConnections.size();
                    for (int i = 0; i < DEFAULT_POOL_SIZE - connectionAmount; i++) {
                        try {
                            Connection connection = ConnectionFactory.createConnection();
                            ProxyConnection proxyConnection = new ProxyConnection(connection);
                            freeConnections.addLast(proxyConnection);
                        } catch (SQLException e) {
                            logger.log(Level.WARN, "Error while creating connection: {}", e.getMessage());
                        }
                    }
                } finally {
                    connectionLock.unlock();
                }
            }
        }, 1000, 1000);
    }
}
