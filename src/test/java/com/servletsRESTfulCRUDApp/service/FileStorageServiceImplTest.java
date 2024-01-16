package com.servletsRESTfulCRUDApp.service;

import com.servletsRESTfulCRUDApp.repository.FileStorageRepository;
import com.servletsRESTfulCRUDApp.service.impl.FileStorageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FileStorageServiceImplTest {

    @Mock
    private FileStorageRepository fileStorageRepository;

    @InjectMocks
    private FileStorageServiceImpl fileStorageService;

    @Test
    void uploadUserFileToStorageShouldInvokeRepository() {
        String fileName = "testFile.txt";
        InputStream mockInputStream = Mockito.mock(InputStream.class);

        fileStorageService.uploadUserFileToStorage(mockInputStream, fileName);

        verify(fileStorageRepository).doUploadUserFileToStorage(eq(mockInputStream), eq(fileName));
    }

    @Test
    void downloadUserFileFromStorageShouldInvokeRepository() {
        String fileName = "testFile.txt";

        fileStorageService.downloadUserFileFromStorage(fileName);

        verify(fileStorageRepository).downloadFileFromStorage(eq(fileName));
    }
}