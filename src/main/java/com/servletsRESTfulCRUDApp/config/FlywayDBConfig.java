package com.servletsRESTfulCRUDApp.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import java.util.Properties;

import static com.servletsRESTfulCRUDApp.config.DBConnection.*;
import static com.servletsRESTfulCRUDApp.config.DBCreator.createDbIfNotExist;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Database.ERR_FLYWAY_MIGRATION_UPDATE_FAILED;

@Slf4j
public class FlywayDBConfig {

    private FlywayDBConfig() {
    }

    static void applyFlywayMigrations() {

        createDbIfNotExist();

        try {
            Properties dbConnectionProperties = getProperties();
            String serverUrl = dbConnectionProperties.getProperty(PROPERTY_DATABASE_URL)
                                + dbConnectionProperties.getProperty(PROPERTY_DATABASE_NAME);

            Flyway flyway = Flyway.configure().dataSource(
                            serverUrl,
                            dbConnectionProperties.getProperty(PROPERTY_DATABASE_USER),
                            dbConnectionProperties.getProperty(PROPERTY_DATABASE_PASSWORD))
                    .load();

            flyway.migrate();
        } catch (FlywayException fwe) {
            log.error(ERR_FLYWAY_MIGRATION_UPDATE_FAILED);
            fwe.printStackTrace(System.out);
            throw fwe;
        }
    }
}
