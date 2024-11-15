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
@RequestMapping("/api/wine/")
public class WineController {

    @Autowired
    private WineService wineService;
    @Autowired
    private WineRepository wineRepository;

    @GetMapping("/getList")
    public ResponseEntity<List<Wine>> getList() {
        try {
            List<Wine> wines = wineService.getAll();
            return ResponseEntity.ok(wines);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getById/{ID}")
    public ResponseEntity<Wine> getWineById(@PathVariable Long ID) {
        try {
            Wine wine = wineService.getWineById(ID);
            return ResponseEntity.ok(wine);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Takes a mock wine object, so that we can receive an ID, in the case that we need to edit an existing wine
     * Catches the exception that a wine was not found in the database with the given ID
     * @param wineData
     * @return List<Wine>
     */
    @PostMapping(value="/admin/createAndEdit", consumes = "application/json")
    public ResponseEntity<List<Wine>> createAndEdit(@RequestBody WineData wineData) {
        System.out.println(wineData.getID());
        System.out.println(wineData.getName());
        System.out.println(wineData.getImageURL());

        Wine wine = new Wine(
                wineData.getDescription(),
                wineData.getImageURL(),
                wineData.getPrice(),
                wineData.getAmountLeft(),
                wineData.getName());

        try{
            return ResponseEntity.ok(wineService.createAndEdit(wine, Long.valueOf(wineData.getID())));
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("admin/delete/{ID}")
    public ResponseEntity<List<Wine>> deleteWine(@PathVariable Long ID){

        try {
            return ResponseEntity.ok(wineService.delete(ID));
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }
}