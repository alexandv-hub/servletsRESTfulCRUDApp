package com.servletsRESTfulCRUDApp.view.messages;

public final class InfoMessages {

    private InfoMessages() {
    }

    public static class Database {
        public static final String INFO_CONNECTED_TO_MYSQL_SERVER_SUCCESSFULLY = "Connected to MySQL Server successfully...";
        public static final String INFO_DATABASE_SUCCESSFULLY_CREATED = "Database successfully created.";
    }

    public static final String INFO_STARTING_INIT_SWAGGER = "Starting init swagger...";
    public static final String INFO_SWAGGER_INIT_FINISHED_SUCCESSFULLY = "Swagger init finished successfully.";

    public static final String INFO_CONNECTING_TO_MY_SQL_SERVER = "Connecting to MySQL Server...";
    public static final String INFO_STARTING_CREATE_NEW_DATABASE = "Starting create new database...";
    public static final String INFO_STARTING_HIBERNATE_SET_UP = "Starting Hibernate set up...";
    public static final String INFO_HIBERNATE_SET_UP_FINISHED_SUCCESSFULLY = "Hibernate set up finished successfully.";

    public static class Entity {
        public static final String INFO_FILE_CREATED = "File Created";
        public static final String INFO_FILE_UPLOADED = "File Uploaded";
        public static final String INFO_FILE_FOUND = "File Found";
        public static final String INFO_FILE_UPDATED_SUCCESSFULLY = "File updated successfully";
        public static final String INFO_FILE_DELETED_SUCCESSFULLY = "File deleted successfully";

        public static final String INFO_EVENT_CREATED = "Event Created";
        public static final String INFO_EVENT_FOUND = "Event Found";
        public static final String INFO_EVENT_UPDATED_SUCCESSFULLY = "Event updated successfully";
        public static final String INFO_EVENT_DELETED_SUCCESSFULLY = "Event deleted Successfully";

        public static final String INFO_USER_CREATED = "User Created";
        public static final String INFO_USER_FOUND = "User Found";
        public static final String INFO_USER_UPDATED_SUCCESSFULLY = "User updated successfully";
        public static final String INFO_USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    }

    public static class FileStorage {
        public static final String INFO_ATTEMPTING_TO_CREATE_DIRECTORY_AT = "Attempting to create directory at: ";
        public static final String INFO_ATTEMPTING_TO_CREATE_FILE_AT = "Attempting to create file at: ";
        public static final String INFO_STARTING_TO_UPLOAD_USER_FILE_TO_FILE_STORAGE = "Starting to upload user file to file storage...";
        public static final String INFO_UPLOADING_USER_FILE_TO_FILE_STORAGE_FINISHED_SUCCESSFULLY = "Uploading user file to file storage finished successfully.";
        public static final String INFO_STARTING_TO_DOWNLOAD_USER_FILE_FROM_FILE_STORAGE = "Starting to download user file from file storage...";
        public static final String INFO_FILE_DOWNLOADED_SUCCESSFULLY = "File downloaded from file storage successfully";
        public static final String INFO_STARTING_TO_SAVE_FILE_REPOSITORY_S_EVENT_ENTITY = "Starting to save file-repository's event entity...";
        public static final String INFO_FILE_REPOSITORY_S_EVENT_ENTITY_SAVED_SUCCESSFULLY = "file-repository's event entity saved successfully.";
    }
}
