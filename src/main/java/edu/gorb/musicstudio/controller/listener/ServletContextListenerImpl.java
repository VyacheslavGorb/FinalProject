package edu.gorb.musicstudio.controller.listener;

import edu.gorb.musicstudio.model.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextListenerImpl implements ServletContextListener {
    /**
     * Initializes {@link ConnectionPool}
     * @param sce servlet context initialization event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    /**
     * Destroys {@link ConnectionPool}
     * @param sce servlet context destroy event
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
