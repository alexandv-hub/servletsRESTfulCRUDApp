package com.servletsRESTfulCRUDApp.config;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.servletsRESTfulCRUDApp.config.PropertiesLoader.loadProperties;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Database.ERR_DB_CONNECTION_FAILED;

@Slf4j
public class DBConnection {
    private static final Properties properties;

    static final String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";

    static final String PROPERTY_NAME_DATABASE_URL = "database.url";
    static final String PROPERTY_NAME_DATABASE_NAME = "database.name";
    static final String PROPERTY_NAME_DATABASE_USER = "database.user";
    static final String PROPERTY_NAME_DATABASE_PASSWORD = "database.password";

    static {
        properties = loadProperties(APPLICATION_PROPERTIES_FILE_NAME);
    }

    private DBConnection() {
    }

    static synchronized Connection getServerConnection() throws SQLException {
        try {
            String serverUrl = properties.getProperty(PROPERTY_NAME_DATABASE_URL);
            return DriverManager.getConnection(
                    serverUrl,
                    properties.getProperty(PROPERTY_NAME_DATABASE_USER),
                    properties.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        } catch (Exception e) {
            log.error(ERR_DB_CONNECTION_FAILED);
            throw e;
        }
    }

    public static synchronized Properties getProperties() {
        return properties;
    }
}
