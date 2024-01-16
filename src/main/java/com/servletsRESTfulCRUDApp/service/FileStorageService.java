package com.servletsRESTfulCRUDApp.service;

import jakarta.ws.rs.core.Response;

import java.io.InputStream;

public interface FileStorageService {

    void uploadUserFileToStorage(InputStream fileInputStream, String fileName);
    Response downloadUserFileFromStorage(String fileName);

}
