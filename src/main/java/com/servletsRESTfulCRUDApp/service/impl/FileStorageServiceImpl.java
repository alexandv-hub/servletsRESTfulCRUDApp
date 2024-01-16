package com.servletsRESTfulCRUDApp.service.impl;

import com.servletsRESTfulCRUDApp.repository.FileStorageRepository;
import com.servletsRESTfulCRUDApp.service.FileStorageService;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
@AllArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    FileStorageRepository fileStorageRepository;

    @Override
    public void uploadUserFileToStorage(InputStream fileInputStream, String fileName) {
        fileStorageRepository.doUploadUserFileToStorage(fileInputStream, fileName);
    }

    @Override
    public Response downloadUserFileFromStorage(String fileName) {
        return fileStorageRepository.downloadFileFromStorage(fileName);
    }
}
