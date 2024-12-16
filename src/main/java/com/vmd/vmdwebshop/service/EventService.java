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
     * createAndEdit
     * This method finds a specific event by Id. If the event already exists, its attributes will be
     * updated with the new values. If the event does not already exist, a new event will be created
     * and saved to the database.
     * @param event object containing the updated data.
     * @param ID of the event.
     * @return List<Event> returns a list of all events.
     * @throws EventDataAccessException if there is failure when retrieving/ saving event data to/from the database.
     * @throws EventNotUpdatedException if there is a failure when updating the event data.
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
                BeanUtils.copyProperties(event, existingEvent, "ID");
                eventRepository.save(existingEvent);
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
     * delete
     * This method finds an event by Id. If no event is found, an exception is thrown.
     * If an event is found, it will attempt to delete it from the database.
     * @param ID
     * @return List<Event> list of all remaining events.
     * @throws EventDataAccessException if failure to retrieve events from the database.
     * @throws EventNotFoundException if no event with the given Id is found.
     * @throws EventNotDeletedException if the event could not be deleted from the database.
     */
    @Override
    public List<Event> delete(Long ID){
        Event existingEvent;
        try {
            Optional<Event> optionalEvent = eventRepository.findById(ID);
            existingEvent = optionalEvent.orElse(null);
        } catch (DataAccessException e) {
            throw new EventDataAccessException("Failed to retrieve the event from the database");
        }

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
