package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethodsInterface;
import com.vmd.vmdwebshop.model.Event;
import com.vmd.vmdwebshop.repository.EventRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements AdministrativeMethodsInterface<Event> {


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

        Event existingEvents = eventRepository.findById(event.getID());

        if (existingEvents != null) {
            existingEvents.setDate(event.getDate());
            existingEvents.setTime(event.getTime());
            existingEvents.setLocation(event.getLocation());
            existingEvents.setTitle(event.getTitle());
            existingEvents.setDescription(event.getDescription());
            existingEvents.setImgURL(event.getImgURL());
            existingEvents.setCancelled(event.isCancelled());
        } else {
            eventRepository.save(event);
        }

        return eventRepository.findAll();
    }

    @Override
    public List<Event> delete(Long ID){
        eventRepository.deleteById(ID);
        return eventRepository.findAll();
    }
}