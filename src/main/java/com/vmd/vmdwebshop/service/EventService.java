package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethods;
import com.vmd.vmdwebshop.model.Event;
import com.vmd.vmdwebshop.repository.EventRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements AdministrativeMethods<Event> {

    private final EventRepository eventRepository;
    private final WineRepository wineRepository;

    @Autowired
    public EventService(EventRepository eventRepository, WineRepository wineRepository) {
        this.eventRepository = eventRepository;
        this.wineRepository = wineRepository;
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> createAndEdit(Event event){
        eventRepository.save(event);
        return eventRepository.findAll();
    }

    @Override
    public List<Event> delete(Event event){
        eventRepository.deleteById(event.getId());
        return eventRepository.findAll();
    }
}