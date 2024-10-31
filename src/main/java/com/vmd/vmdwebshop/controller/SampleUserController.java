// src/main/java/com/vmd/vmdwebshop/controller/SampleUserController.java
package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/base") // Base URL for user-related endpoints
public class SampleUserController {

    @Autowired
    private WineService wineService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hallo World");
    }

    @GetMapping("/wine")
    public ResponseEntity<List<Wine>> wine() {
        List<Wine> wines = wineService.getAllWines();
        return ResponseEntity.ok(wines);
    }
}