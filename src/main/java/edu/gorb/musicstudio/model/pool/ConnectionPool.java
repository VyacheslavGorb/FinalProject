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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();

    private static ConnectionPool instance;
    private static AtomicBoolean isInitialised = new AtomicBoolean(false);

    private static final int DEFAULT_POOL_SIZE = 32;
    private static final int DEFAULT_MILLISECONDS_DELAY = 1000;
    private static final int DEFAULT_MILLISECONDS_INTERVAL = 1000;
    private static final boolean DEFAULT_IS_VALIDATION_TASK_USED = false;

    private static final String PROPERTY_FILE_PATH = "properties/pool.properties";
    private static final String IS_VALIDATION_TASK_USED_PROPERTY = "is_connection_amount_validation_task_used";
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
    private boolean isValidationTaskUsed;

    private ConnectionPool() {
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_PATH)) {
            if (inputStream == null) {
                logger.log(Level.WARN, "Pool properties file not found. Default values are used.");
                assignDefaultValues();
            } else {
                Properties properties = new Properties();
                properties.load(inputStream);
                poolSize = Integer.parseInt(properties.getProperty(POOL_SIZE_PROPERTY));
                timerDelay = Integer.parseInt(properties.getProperty(TASK_DELAY_PROPERTY));
                timerInterval = Integer.parseInt(properties.getProperty(TASK_INTERVAL_PROPERTY));
                isValidationTaskUsed = Boolean.parseBoolean(properties.getProperty(IS_VALIDATION_TASK_USED_PROPERTY));
            }
        } catch (IOException e) {
            logger.log(Level.WARN, "Error while closing properties file");
            assignDefaultValues();
        } catch (NumberFormatException e) {
            logger.log(Level.WARN, "Error while converting pool properties. Default values are used.");
            assignDefaultValues();
        }

        connectionLock = new ReentrantLock(true);
        freeConnectionCondition = connectionLock.newCondition();
        freeConnections = new ArrayDeque<>(poolSize);
        givenAwayConnections = new ArrayDeque<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.addLast(proxyConnection);
            } catch (SQLException e) {
                logger.log(Level.WARN, "Error while creating connection: {}", e.getMessage());
            }
        }

        if (freeConnections.isEmpty()) {
            logger.fatal("Unable to create connections");
            throw new RuntimeException("Unable to create connections");
        }

        logger.debug("{} connections created", freeConnections.size());

        if (isValidationTaskUsed) {
            setConnectionAmountValidationTask();
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

    public Connection getConnection() throws DatabaseConnectionException {
        connectionLock.lock();
        ProxyConnection connection;
        try {
            while (freeConnections.isEmpty()) {
                freeConnectionCondition.await();
            }
            connection = freeConnections.pollFirst();
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
            freeConnectionCondition.signal();
        } finally {
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

    private void assignDefaultValues() {
        poolSize = DEFAULT_POOL_SIZE;
        timerDelay = DEFAULT_MILLISECONDS_DELAY;
        timerInterval = DEFAULT_MILLISECONDS_INTERVAL;
        isValidationTaskUsed = DEFAULT_IS_VALIDATION_TASK_USED;
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
        ConnectionAmountValidationTask validationTask =
                new ConnectionAmountValidationTask(connectionLock, freeConnections, givenAwayConnections, poolSize);
        timer.schedule(validationTask, timerDelay, timerInterval);
    }
}
