package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Event;
import com.vmd.vmdwebshop.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    // Constructor
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping("/admin/createAndEdit")
    public ResponseEntity<List<Event>> createAndEdit(@RequestBody @Valid Event event) {
        return ResponseEntity.ok(eventService.createAndEdit(event));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @PostMapping("/admin/delete/{ID}")
    public ResponseEntity<List<Event>> delete(@PathVariable ("ID") Long ID) {
        return ResponseEntity.ok(eventService.delete(ID));
    }




}