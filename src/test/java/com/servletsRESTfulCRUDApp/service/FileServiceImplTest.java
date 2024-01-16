package com.servletsRESTfulCRUDApp.service;

import com.servletsRESTfulCRUDApp.model.File;
import com.servletsRESTfulCRUDApp.repository.FileRepository;
import com.servletsRESTfulCRUDApp.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    void testSaveFile() {
        File file = File.builder().build();
        when(fileRepository.save(any(File.class))).thenReturn(Optional.ofNullable(file));

        Optional<File> savedFile = fileService.save(file);

        assertNotNull(savedFile);
        verify(fileRepository).save(file);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        File file = File.builder().build();
        when(fileRepository.findById(id)).thenReturn(Optional.of(file));

        Optional<File> foundFile = fileService.findById(id);

        assertTrue(foundFile.isPresent());
        assertEquals(file, foundFile.get());
        verify(fileRepository).findById(id);
    }

    @Test
    void testFindAll() {
        File file = File.builder().build();
        when(fileRepository.findAll()).thenReturn(Collections.singletonList(file));

        List<File> files = fileService.findAll();

        assertFalse(files.isEmpty());
        assertEquals(1, files.size());
        verify(fileRepository).findAll();
    }

    @Test
    void testUpdateFile() {
        File file = File.builder().build();
        when(fileRepository.update(file)).thenReturn(Optional.of(file));

        Optional<File> updatedFile = fileService.update(file);

        assertTrue(updatedFile.isPresent());
        verify(fileRepository).update(file);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        when(fileRepository.deleteById(id)).thenReturn(true);

        boolean isDeleted = fileService.deleteById(id);

        assertTrue(isDeleted);
        verify(fileRepository).deleteById(id);
    }
}
