package com.servletsRESTfulCRUDApp.service;

import com.servletsRESTfulCRUDApp.model.Event;
import com.servletsRESTfulCRUDApp.repository.EventRepository;
import com.servletsRESTfulCRUDApp.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void testSaveEvent() {
        Event event = Event.builder().build();
        when(eventRepository.save(event)).thenReturn(Optional.ofNullable(event));

        Optional<Event> savedEvent = eventService.save(event);

        assertNotNull(savedEvent);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Optional<Event> eventOptional = Optional.of(Event.builder().build());
        when(eventRepository.findById(id)).thenReturn(eventOptional);

        Optional<Event> foundEvent = eventService.findById(id);

        assertTrue(foundEvent.isPresent());
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void testFindAllEvents() {
        List<Event> eventsList = Arrays.asList(Event.builder().build(), Event.builder().build());
        when(eventRepository.findAll()).thenReturn(eventsList);

        List<Event> foundEvents = eventService.findAll();

        assertNotNull(foundEvents);
        assertEquals(2, foundEvents.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEvent() {
        Event eventToUpdate = Event.builder().id(1L).build();
        Event updatedEvent = Event.builder().id(1L).build(); // Предполагается, что объект обновлен
        when(eventRepository.update(eventToUpdate)).thenReturn(Optional.of(updatedEvent));

        Optional<Event> result = eventService.update(eventToUpdate);

        assertTrue(result.isPresent());
        assertEquals(updatedEvent, result.get());
        verify(eventRepository, times(1)).update(eventToUpdate);
    }

    @Test
    void testDeleteEventByIdSuccessfully() {
        Long id = 1L;
        when(eventRepository.deleteById(id)).thenReturn(true);

        boolean isDeleted = eventService.deleteById(id);

        assertTrue(isDeleted);
        verify(eventRepository, times(1)).deleteById(id);
    }
}
