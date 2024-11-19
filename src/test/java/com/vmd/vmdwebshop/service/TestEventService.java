package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.repository.EventRepository;
import com.vmd.vmdwebshop.model.Event;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

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

    }

    @Test
    public void TestGetAll() {}

    @Test
    public void TestCreateAndEdit01() {}

    @Test
    public void TestCreateAndEdit02() {}

    @Test
    public void TestCreateAndEdit03() {}

    @Test
    public void TestDelete01() {}

    @Test
    public void TestDelete02() {}

}


