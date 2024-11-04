// src/main/java/com/vmd/vmdwebshop/controller/WineController.java
package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping(value = "/api/getAllWines")
    public ResponseEntity<List<Wine>> wine() {
        List<Wine> wines = wineService.getAllWines();
        return ResponseEntity.ok(wines);
    }

    @GetMapping("/api/getWineById/{id}")
    public ResponseEntity<Wine> getWineById(@PathVariable Long id) {
        Wine wine = wineService.getWineById(id);
        if (wine != null) {
            return ResponseEntity.ok(wine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/test")
    public ResponseEntity<String> testWine(@RequestBody String id) {
        return ResponseEntity.ok(id);
    }
}