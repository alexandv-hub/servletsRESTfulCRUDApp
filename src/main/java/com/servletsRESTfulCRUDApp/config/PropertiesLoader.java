package com.servletsRESTfulCRUDApp.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Database.ERR_SORRY_UNABLE_TO_FIND_FILE;

@Slf4j
public class PropertiesLoader {

    static Properties loadProperties(String propertiesFileName) {
        Properties properties = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                log.error(ERR_SORRY_UNABLE_TO_FIND_FILE + propertiesFileName);
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
        return properties;
    }
}
