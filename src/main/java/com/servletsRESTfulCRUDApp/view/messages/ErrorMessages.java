package com.servletsRESTfulCRUDApp.view.messages;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static class Database {
        public static final String ERR_SORRY_UNABLE_TO_FIND_FILE = "Sorry, unable to find file: ";
        public static final String ERR_DB_CONNECTION_FAILED = "DB connection failed!";
        public static final String ERR_NO_DATABASE_FOUND = "No database found!";
        public static final String ERR_DATABASE_CREATION_FAILED = "Database creation failed!";
        public static final String ERR_FLYWAY_MIGRATION_UPDATE_FAILED = "Flyway migration update failed!";
        public static final String ERR_WHEN_SETTING_UP_HIBERNATE_SESSION_FACTORY = "Error when setting up Hibernate SessionFactory!";
    }

    public static final String ERR_SWAGGER_INIT_FAILED = "Swagger init failed!";

    public static final String ERR_500_INTERNAL_SERVER_ERROR = "Internal Server Error";

    public static class Entity {
        public static final String ERR_INVALID_ID_SUPPLIED = "Invalid ID supplied";
        public static final String ERR_INVALID_PATH_ID_IS_REQUIRED = "Invalid path. ID is required.";

        public static final String ERR_EVENT_NOT_FOUND = "Event not found";
        public static final String ERR_EVENT_DATA_IS_REQUIRED = "Event data is required";
        public static final String ERR_UPDATING_EVENT = "Error updating event: ";
        public static final String ERR_DELETING_EVENT = "Error deleting event: ";

        public static final String ERR_FILE_NOT_FOUND = "File not found";
        public static final String ERR_FILE_DATA_IS_REQUIRED = "File data is required";
        public static final String ERR_UPDATING_FILE = "Error updating file: ";
        public static final String ERR_DELETING_FILE = "Error deleting file: ";

        public static final String ERR_USER_NOT_FOUND = "User not found";
        public static final String ERR_USER_DATA_IS_REQUIRED = "User data is required";
        public static final String ERR_UPDATING_USER = "Error updating user: ";
        public static final String ERR_DELETING_USER = "Error deleting user: ";
    }

    public static class FileStorage {
        public static final String ERR_CREATING_FILE_STORAGE_DIRECTORY = "Error creating file storage directory";
        public static final String ERR_UPLOADING_FILE = "Error uploading file";
        public static final String ERR_OCCURRED_WHILE_PROCESSING_FILE = "Error occurred while processing file";
    }
}
