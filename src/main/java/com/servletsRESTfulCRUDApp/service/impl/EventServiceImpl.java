package com.servletsRESTfulCRUDApp.service.impl;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.repository.EventRepository;
import com.servletsRESTfulCRUDApp.service.EventService;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

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
}
