package com.servletsRESTfulCRUDApp.service.impl;

import com.servletsRESTfulCRUDApp.model.File;
import com.servletsRESTfulCRUDApp.repository.FileRepository;
import com.servletsRESTfulCRUDApp.service.FileService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;

    @Override
    public Optional<File> save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public Optional<File> findById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<File> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public Optional<File> update(File file) {
        return fileRepository.update(file);
    }

    @Override
    public boolean deleteById(Long id) {
        return fileRepository.deleteById(id);
    }
}
