package edu.gorb.musicstudio.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties dbProperties = new Properties();
    private static final String DB_PROPERTY_FILE = "properties/db.properties";
    private static final String DB_URL_PROPERTY = "url";
    private static final String DB_DRIVER_PROPERTY = "driver";
    private static final String DB_URL;

    static {
        try (InputStream inputStream = ConnectionFactory.class.getResourceAsStream(DB_PROPERTY_FILE)) {
            dbProperties.load(inputStream);
            DB_URL = dbProperties.getProperty(DB_URL_PROPERTY);
            String driver = dbProperties.getProperty(DB_DRIVER_PROPERTY);
            Class.forName(driver);
        } catch (IOException e) {
            logger.log(Level.FATAL, "Error while reading property file");
            throw new RuntimeException("Error while reading property file"); // TODO | or default
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "Error while driver registration");
            throw new RuntimeException("Error while driver registration");
        }
    }

    private ConnectionFactory() {
    }

    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, dbProperties);
    }
}
