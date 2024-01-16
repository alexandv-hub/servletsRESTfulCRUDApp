package com.servletsRESTfulCRUDApp.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LoggingConfig.configure();
        FlywayDBConfig.applyFlywayMigrations();
    }
}
