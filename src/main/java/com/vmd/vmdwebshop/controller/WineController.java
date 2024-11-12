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

    @GetMapping("/api/getAllWines")
    public ResponseEntity<List<Wine>> wine() {
        List<Wine> wines = wineService.getAll();
        return ResponseEntity.ok(wines);
    }

    @GetMapping("/api/getWineById/{wineID}")
    public ResponseEntity<Wine> getWineById(@PathVariable Long wineID) {
        Wine wine = wineService.getWineById(wineID);
        if (wine != null) {
            return ResponseEntity.ok(wine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/createAndEditWine")
    public ResponseEntity<List<Wine>> createWine(@RequestBody Wine wine) {
        return ResponseEntity.ok(wineService.createAndEdit(wine));
    }

    @PostMapping("/api/deleteWine")
    public ResponseEntity<List<Wine>> deleteWine(@RequestBody Wine wine){
        return ResponseEntity.ok(wineService.delete(wine));
    }
}