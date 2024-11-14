// src/main/java/com/vmd/vmdwebshop/controller/WineController.java
package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import com.vmd.vmdwebshop.service.WineData;
import com.vmd.vmdwebshop.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class WineController {

    @Autowired
    private WineService wineService;
    @Autowired
    private WineRepository wineRepository;

    @GetMapping("/getAllWines")
    public ResponseEntity<List<Wine>> wine() {
        List<Wine> wines = wineService.getAll();
        return ResponseEntity.ok(wines);
    }

    @GetMapping("/getWineById/{wineID}")
    public ResponseEntity<Wine> getWineById(@PathVariable Long wineID) {
        Wine wine = wineService.getWineById(wineID);
        if (wine != null) {
            return ResponseEntity.ok(wine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Takes a mock wine object, so that we can receive an ID, in the case that we need to edit an existing wine
     * Catches the exception that a wine was not found in the database with the given ID
     * @param wineData
     * @return List<Wine>
     */
    @PostMapping("/createAndEditWine")
    public ResponseEntity<List<Wine>> createWine(@RequestBody WineData wineData) {

        Wine wine = new Wine(
                wineData.getDescription(),
                wineData.getImageURL(),
                wineData.getPrice(),
                wineData.getAmountLeft(),
                wineData.getName());

        try{
            return ResponseEntity.ok(wineService.createAndEdit(wine, wineData.getID()));
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.ok(wineRepository.findAll());
        }

    }

    @PostMapping("/deleteWine/{ID}")
    public ResponseEntity<List<Wine>> deleteWine(@PathVariable Long ID){

        try {
            return ResponseEntity.ok(wineService.delete(ID));
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.ok(wineRepository.findAll());
        }

    }
}