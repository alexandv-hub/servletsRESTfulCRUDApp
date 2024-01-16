package com.servletsRESTfulCRUDApp.config;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static com.servletsRESTfulCRUDApp.config.DBConnection.*;
import static com.servletsRESTfulCRUDApp.repository.impl.hibernate.SQLQueries.*;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Database.ERR_DATABASE_CREATION_FAILED;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Database.ERR_NO_DATABASE_FOUND;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.Database.INFO_CONNECTED_TO_MYSQL_SERVER_SUCCESSFULLY;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.Database.INFO_DATABASE_SUCCESSFULLY_CREATED;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.INFO_CONNECTING_TO_MY_SQL_SERVER;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.INFO_STARTING_CREATE_NEW_DATABASE;

@Slf4j
public class DBCreator {

    private static final String PROPERTY_DB_DRIVER_NAME = getProperties().getProperty("database.driver.name");

    private DBCreator() {
    }

    static void createDbIfNotExist() {
        log.info(INFO_CONNECTING_TO_MY_SQL_SERVER);

        try {
            Class.forName(PROPERTY_DB_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = getServerConnection();
             Statement statement = connection.createStatement()) {

            Properties dbConnectionProperties = getProperties();

            ResultSet resultSet = statement.executeQuery(SQL_SHOW_DATABASES);
            boolean dbExists = false;
            while (resultSet.next()) {
                if (dbConnectionProperties.getProperty(PROPERTY_NAME_DATABASE_NAME).equals(resultSet.getString(1))) {
                    dbExists = true;
                    log.info(INFO_CONNECTED_TO_MYSQL_SERVER_SUCCESSFULLY);
                    break;
                }
            }

            if (!dbExists) {
                log.error(ERR_NO_DATABASE_FOUND);
                log.info(INFO_STARTING_CREATE_NEW_DATABASE);
                statement.executeUpdate(SQL_CREATE_DATABASE + dbConnectionProperties.getProperty(PROPERTY_NAME_DATABASE_NAME));
                log.info(INFO_DATABASE_SUCCESSFULLY_CREATED);
            }
        } catch (Exception e) {
            log.error(ERR_DATABASE_CREATION_FAILED);
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }
}
