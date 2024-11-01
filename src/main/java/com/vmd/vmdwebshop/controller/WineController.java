// src/main/java/com/vmd/vmdwebshop/controller/WineController.java
package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping("/api/getAllWines")
    public List<Wine> getAllWines() {
        return wineService.getAllWines();
    }

    @GetMapping("/api/getWineById/{id}")
    public Wine getWineById(@PathVariable Long id) {
        return wineService.getWineById(id);
    }
}