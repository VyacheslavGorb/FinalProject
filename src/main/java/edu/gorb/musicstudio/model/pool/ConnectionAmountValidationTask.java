package edu.gorb.musicstudio.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Deque;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;

class ConnectionAmountValidationTask extends TimerTask {
    private static final Logger logger = LogManager.getLogger();

    private Lock connectionLock;
    private Deque<ProxyConnection> freeConnections;
    private Deque<ProxyConnection> givenAwayConnections;
    private int poolSize;

    ConnectionAmountValidationTask(Lock connectionLock, Deque<ProxyConnection> freeConnections,
                                   Deque<ProxyConnection> givenAwayConnections, int poolSize) {
        this.connectionLock = connectionLock;
        this.freeConnections = freeConnections;
        this.givenAwayConnections = givenAwayConnections;
        this.poolSize = poolSize;
    }

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
}
