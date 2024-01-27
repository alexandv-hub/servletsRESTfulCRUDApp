package com.servletsRESTfulCRUDApp.service.impl;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.model.File;
import com.servletsRESTfulCRUDApp.model.User;
import com.servletsRESTfulCRUDApp.repository.EventRepository;
import com.servletsRESTfulCRUDApp.service.EventService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.servletsRESTfulCRUDApp.repository.impl.FileStorageRepositoryImpl.PROPERTY_FILE_STORAGE_DIR;

@AllArgsConstructor
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;

    @Override
    public Optional<Event> save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> update(Event event) {
        return eventRepository.update(event);
    }

    @Override
    public boolean deleteById(Long id) {
        return eventRepository.deleteById(id);
    }

    @Override
    public Event prepareNewEntity(User user, String fileName) {
        File file = File.builder()
                .name(fileName)
                .filePath(PROPERTY_FILE_STORAGE_DIR)
                .build();

        return Event.builder()
                .user(user)
                .file(file)
                .build();
    }
}
