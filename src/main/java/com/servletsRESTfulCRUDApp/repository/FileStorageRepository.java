package com.servletsRESTfulCRUDApp.repository;

import jakarta.ws.rs.core.Response;

import java.io.InputStream;

public interface FileStorageRepository {

    void doUploadUserFileToStorage(InputStream fileInputStream, String fileName);
    Response downloadFileFromStorage(String fileId);

}
