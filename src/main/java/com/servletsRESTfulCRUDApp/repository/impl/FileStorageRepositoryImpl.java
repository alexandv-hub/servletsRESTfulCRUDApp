package com.servletsRESTfulCRUDApp.repository.impl;

import com.servletsRESTfulCRUDApp.repository.FileStorageRepository;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.servletsRESTfulCRUDApp.config.DBConnection.getProperties;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.Entity.ERR_FILE_NOT_FOUND;
import static com.servletsRESTfulCRUDApp.view.messages.ErrorMessages.FileStorage.*;
import static com.servletsRESTfulCRUDApp.view.messages.InfoMessages.FileStorage.*;

@Slf4j
public class FileStorageRepositoryImpl implements FileStorageRepository {

    public static final String PROPERTY_FILE_STORAGE_DIR = getProperties().getProperty("file.storage.dir");

    @Override
    public void doUploadUserFileToStorage(InputStream fileInputStream, String fileName) {
        log.info(INFO_STARTING_TO_UPLOAD_USER_FILE_TO_FILE_STORAGE);

        Path storagePath = Paths.get(PROPERTY_FILE_STORAGE_DIR);
        if (!Files.exists(storagePath)) {
            log.info(INFO_ATTEMPTING_TO_CREATE_DIRECTORY_AT + storagePath);
            try {
                Files.createDirectories(storagePath);
            } catch (IOException e) {
                log.error(ERR_CREATING_FILE_STORAGE_DIRECTORY, e);
                return;
            }
        }

        Path filePath = storagePath.resolve(fileName);
        log.info(INFO_ATTEMPTING_TO_CREATE_FILE_AT + filePath);
        File file = filePath.toFile();
        try (OutputStream outStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            log.info(INFO_UPLOADING_USER_FILE_TO_FILE_STORAGE_FINISHED_SUCCESSFULLY);
        } catch (IOException e) {
            log.error(ERR_UPLOADING_FILE, e);
        }
    }

    @Override
    public Response downloadFileFromStorage(String fileName) {
        log.info(INFO_STARTING_TO_DOWNLOAD_USER_FILE_FROM_FILE_STORAGE);

        File file = new File(PROPERTY_FILE_STORAGE_DIR + File.separator + fileName);

        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ERR_FILE_NOT_FOUND)
                    .build();
        }

        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ERR_OCCURRED_WHILE_PROCESSING_FILE)
                    .build();
        }

        String contentDisposition = "attachment; filename=\"" + fileName + "\"";

        log.info(INFO_FILE_DOWNLOADED_SUCCESSFULLY);
        return Response.ok(fileInputStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", contentDisposition)
                .build();
    }
}
