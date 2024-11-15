// src/main/java/com/vmd/vmdwebshop/controller/WineController.java
package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import com.vmd.vmdwebshop.service.WineDTO;
import com.vmd.vmdwebshop.service.WineService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public ResponseEntity<Wine> getWineById(@PathVariable("ID") @Pattern(regexp = "^\\d+$") @Size(max = 10) String ID) {
        try {
            Wine wine = wineService.getWineById(Long.parseLong(ID));
            return ResponseEntity.ok(wine);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Takes a mock wine object, so that we can receive an ID, in the case that we need to edit an existing wine
     * Catches the exception that a wine was not found in the database with the given ID
     * @param wineDTO
     * @return List<Wine>
     */
    @PostMapping(value="/admin/createAndEdit", consumes = "application/json")
    public ResponseEntity<List<Wine>> createAndEdit(@RequestBody @Valid WineDTO wineDTO) {
        Wine wine = wineDTO.createWineFromWineData();

        try{
            return ResponseEntity.ok(wineService.createAndEdit(wine, Long.valueOf(wineDTO.getID())));
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping("admin/delete/{ID}")
    public ResponseEntity<List<Wine>> deleteWine(@PathVariable("ID") @Pattern(regexp = "^\\d+$") String ID){

        try {
            return ResponseEntity.ok(wineService.delete(Long.parseLong(ID)));

        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }
}