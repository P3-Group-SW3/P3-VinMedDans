package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethodsInterface;
import com.vmd.vmdwebshop.exception.event.*;
import com.vmd.vmdwebshop.model.Event;
import com.vmd.vmdwebshop.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService implements AdministrativeMethodsInterface<Event> {


    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * getAll
     * This method finds and returns all existing events in the database.
     * If no events are found, an exception will be thrown.
     * @return list of all events in the database.
     * @exception EventNotFoundException is thrown if an event cannot be retrieved from the database.
     */
    @Override
    public List<Event> getAll() {
        List<Event> eventList;

        try {
            eventList = eventRepository.findAll();
        } catch (DataAccessException e) {
            throw new EventDataAccessException("Failure to retrieve events from the database");
        }

        if(eventList.isEmpty()){
            System.out.println("There are currently no events in the database");
        }

        return eventList;
    }

    /**
     *
     * @param event
     * @param ID
     * @return
     */
    @Override
    public List<Event> createAndEdit(Event event, Long ID){

        Event existingEvent;

        try {
            Optional<Event> optionalEvent = eventRepository.findById(ID);
            existingEvent = optionalEvent.orElse(null);}
        catch (DataAccessException e) {
            throw new EventDataAccessException("Failed to retrieve the event from the database");}

        try {

            if (existingEvent != null) {
                existingEvent.setDate(event.getDate());
                existingEvent.setTime(event.getTime());
                existingEvent.setLocation(event.getLocation());
                existingEvent.setTitle(event.getTitle());
                existingEvent.setDescription(event.getDescription());
                existingEvent.setImgURL(event.getImgURL());
                existingEvent.setCancelled(event.isCancelled());
                eventRepository.save(event);
            } else {
                eventRepository.save(event);
            }

        }catch (DataIntegrityViolationException e) {
            throw new EventNotUpdatedException("Failed to update event");
        }catch (DataAccessException e) {
            throw new EventDataAccessException("Failed to save event to the database");}

        return eventRepository.findAll();
    }

    /**
     *
     * @param ID
     * @return
     */
    @Override
    public List<Event> delete(Long ID){
            Optional<Event> optionalEvent = eventRepository.findById(ID);
            Event existingEvent = optionalEvent.orElse(null);

            if(existingEvent == null){
                throw new NullPointerException("No such event exists");
            }
            try {
                eventRepository.deleteById(ID);
            } catch (DataAccessException e) {
                throw new EventNotDeletedException("Failed to delete event in the database");
            }

        return eventRepository.findAll();
    }
}