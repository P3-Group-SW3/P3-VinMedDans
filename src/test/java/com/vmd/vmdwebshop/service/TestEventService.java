package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.event.EventDataAccessException;
import com.vmd.vmdwebshop.exception.event.EventNotUpdatedException;
import com.vmd.vmdwebshop.repository.EventRepository;
import com.vmd.vmdwebshop.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestEventService {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    List<Event> eventList = new ArrayList<>() {};

    Event event1;
    Event event2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // initalises mocks before each test

        event1 = new Event("01/01/2025", "1800", "location", "title",
                "description", "imgURL");

        eventList.clear();
        eventList.add(event1);
    }

    /** Test that getAll method returns a list of all events in the database */
    @Test
    public void TestGetAll01() {
        when(eventRepository.findAll())
                .thenReturn(eventList);

        List<Event> events = eventService.getAll();

        assertNotNull(events);
    }

    /** Test that getAll method returns an empty list if no events currently exist */
    @Test
    public void TestGetAll02() {
        when(eventRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Event> events = eventService.getAll();

        assertTrue(events.isEmpty());
    }

    /** Test that getAll method throws an exception if there is a failure when accessing the database */
    @Test
    public void TestGetAll03() {
        when(eventRepository.findAll())
                .thenThrow(DataAccessResourceFailureException.class);

        assertThrows(EventDataAccessException.class, () -> eventService.getAll());
    }


    /** Test that when the createAndEdit method is called, the service returns a list of events*/
    @Test
    public void TestCreateAndEdit01() {
        when(eventRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(event1));

        List<Event> events = eventService.createAndEdit(event1, Long.parseLong("1"));

        assertNotNull(events);
    }

    /** Test that createAndEdit method throws an exception if there is a failure when accessing the database */
    @Test
    public void TestCreateAndEdit02() {
        when(eventRepository.findById(Long.parseLong("1")))
                .thenThrow(DataAccessResourceFailureException.class);

        assertThrows(EventDataAccessException.class, () -> eventService.createAndEdit(event1,Long.parseLong("1")));
    }

    /** Test that createAndEdit method throws an exception if the method failed to update the event */
    @Test
    public void TestCreateAndEdit03() {
        when(eventRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(event1));

        doThrow(DataIntegrityViolationException.class)
                .when(eventRepository).save(event1);

        assertThrows(EventNotUpdatedException.class, () -> eventService.createAndEdit(event1, Long.parseLong("1")));
    }

    /** Test that createAndEdit method throws an exception if the method fails to save the event */
    @Test
    public void TestCreateAndEdit04() {
        when(eventRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(event1));

        doThrow(DataAccessResourceFailureException.class)
                .when(eventRepository).save(event1);

        assertThrows(EventDataAccessException.class, () -> eventService.createAndEdit(event1, Long.parseLong("1")));
    }


    /** Test that delete method returns a list of all remaining events after deletion */
    @Test
    public void TestDelete01() {
        when(eventRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(event1));

        List<Event> events = eventService.delete(Long.parseLong("1"));

        assertNotNull(events);
    }

    /** Test whether an exception is thrown when a specific event does not exist */
    @Test
    public void TestDelete02 () {
        when(eventRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> eventService.delete(Long.parseLong("1")));
    }
}
