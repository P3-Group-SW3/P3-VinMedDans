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

    @GetMapping("/getList")
    public ResponseEntity<List<Event>> getAll() {
        try {
            return ResponseEntity.ok(eventService.getAll());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/createAndEdit/{ID}")
    public ResponseEntity<List<Event>> createAndEdit(@PathVariable ("ID") Long ID, @RequestBody @Valid Event event) {
        System.out.println(event.getDate());
        System.out.println(event.getTime());
        System.out.println(event.getLocation());
        System.out.println(event.getTitle());
        System.out.println(event.getDescription());
        System.out.println(event.getImgURL());
        try {
            return ResponseEntity.ok(eventService.createAndEdit(event, ID));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/admin/delete/{ID}")
    public ResponseEntity<List<Event>> delete(@PathVariable ("ID") Long ID) {
        try {
            return ResponseEntity.ok(eventService.delete(ID));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }





}