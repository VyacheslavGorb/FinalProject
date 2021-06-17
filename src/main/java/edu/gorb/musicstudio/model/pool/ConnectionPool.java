package edu.gorb.musicstudio.model.pool;

import edu.gorb.musicstudio.exception.DatabaseConnectionException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool { // TODO fix work with queue
    private static final Logger logger = LogManager.getLogger();

    private static ConnectionPool instance;
    private static AtomicBoolean isInitialised = new AtomicBoolean(false);

    private static final int DEFAULT_POOL_SIZE = 32;
    private static final int DEFAULT_MILLISECONDS_DELAY = 1000;
    private static final int DEFAULT_MILLISECONDS_INTERVAL = 1000;

    private static final String PROPERTY_FILE_PATH = "properties/pool.properties";
    private static final String TASK_DELAY_PROPERTY = "task_delay";
    private static final String TASK_INTERVAL_PROPERTY = "task_interval";
    private static final String POOL_SIZE_PROPERTY = "pool_size";


    private Deque<ProxyConnection> freeConnections;
    private Deque<ProxyConnection> givenAwayConnections;
    private Lock connectionLock;
    private Condition freeConnectionCondition;

    private int poolSize;
    private int timerDelay;
    private int timerInterval;


    private ConnectionPool() {
        try (InputStream inputStream = ConnectionPool.class.getResourceAsStream(PROPERTY_FILE_PATH)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            poolSize = Integer.parseInt(properties.getProperty(POOL_SIZE_PROPERTY));
            timerDelay = Integer.parseInt(properties.getProperty(TASK_DELAY_PROPERTY));
            timerInterval = Integer.parseInt(properties.getProperty(TASK_INTERVAL_PROPERTY));
        } catch (IOException | NumberFormatException e) {
            logger.log(Level.ERROR, "Error while reading pool properties");
            poolSize = DEFAULT_POOL_SIZE;
            timerDelay = DEFAULT_MILLISECONDS_DELAY;
            timerInterval = DEFAULT_MILLISECONDS_INTERVAL;
        }

        connectionLock = new ReentrantLock(true);
        freeConnectionCondition = connectionLock.newCondition();
        freeConnections = new ArrayDeque<>();
        givenAwayConnections = new ArrayDeque<>();
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException e) {
                logger.log(Level.WARN, "Error while creating connection: {}", e.getMessage());
            }
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("Unable to create connections");
            throw new RuntimeException("Unable to create connections");
        }
        setConnectionAmountValidationTask();
    }

    public static ConnectionPool getInstance() {
        while (instance == null) {
            if (isInitialised.compareAndSet(false, true)) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    public Connection getConnection() throws DatabaseConnectionException {
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
            throw new DatabaseConnectionException("Thread is interrupted" + e.getMessage());
        } finally {
            connectionLock.unlock();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            logger.log(Level.ERROR, "Illegal connection type");
            return false;
        }
        connectionLock.lock();
        try {
            givenAwayConnections.remove(connection);
            freeConnections.addLast((ProxyConnection) connection);
        } finally {
            freeConnectionCondition.signal();
            connectionLock.unlock();
        }
        return true;
    }

    public void destroyPool() {
        connectionLock.lock();
        try {
            freeConnections.forEach(ProxyConnection::reallyClose);
            givenAwayConnections.forEach(ProxyConnection::reallyClose);
            freeConnections.clear();
            givenAwayConnections.clear();
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

    private void setConnectionAmountValidationTask() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { // TODO Move to separate file
            @Override
            public void run() {
                connectionLock.lock();
                try {
                    int connectionAmount = freeConnections.size() + givenAwayConnections.size();
                    for (int i = 0; i < poolSize - connectionAmount; i++) {
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
        }, timerDelay, timerInterval);
    }
}
