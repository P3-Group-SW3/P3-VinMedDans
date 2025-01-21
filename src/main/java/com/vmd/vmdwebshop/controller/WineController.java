// src/main/java/com/vmd/vmdwebshop/controller/WineController.java
package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.service.WineService;
import jakarta.validation.Valid;
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

    /**
     * Request for a list of all wines in the database.
     * @return List<Wine>
     */
    @GetMapping("/getList")
    public ResponseEntity<List<Wine>> getList() {
        try {
            List<Wine> wines = wineService.getAll(); //retrieves all wines

            return ResponseEntity.ok(wines); //creates the response
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build(); //builds a response with a status 404
        }
    }

    /**
     * Request for a specific wine object
     * Receives the ID as a path variable
     * @param ID
     * @return wine object
     */
    @GetMapping("/getById/{ID}")
    public ResponseEntity<Wine> getWineById(@PathVariable("ID") @Pattern(regexp = "^\\d+$") @Size(max = 10) String ID) {
        //The @Pattern annotation validates the input against the regex
        //the input string must only contain digits
        try {
            Wine wine = wineService.getWineById(Long.parseLong(ID)); //receives the ID as a String, parses to a LONG

            return ResponseEntity.ok(wine); //creates the response
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build(); //builds a response with a status 404
        }
    }

    /**
     * Takes a wine object and an ID in the path Variable
     * The path variable is used if the request is used to update an existing wine.
     * The ID cannot be passed onto a wine object directly
     * Catches exceptions that there were errors in retrieving, updating or saving objects in the databse
     * @param wine object
     * @return List<Wine>
     */
    @PostMapping(value="/admin/createAndEdit/{ID}")
    public ResponseEntity<List<Wine>> createAndEdit(@PathVariable ("ID") Long ID, @RequestBody @Valid Wine wine) {
        try{
            return ResponseEntity.ok(wineService.createAndEdit(wine, ID)); //creates the response
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build(); //builds a response with status code 500
        }
    }

    /**
     * This method deletes a wine in the database by its ID.
     * It retrieves the ID as a path variable
     * @param ID
     * @return List<Wine>
     */
    @PostMapping("admin/delete/{ID}")
    public ResponseEntity<List<Wine>> deleteWine(@PathVariable("ID") @Pattern(regexp = "^\\d+$") String ID) {
        //The @Pattern annotation validates the input against the regex
        //the input string must only contain digits
        try {
            return ResponseEntity.ok(wineService.delete(Long.parseLong(ID))); //creates the response
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build(); //builds a response with status code 500
        }
    }

    /**This method changes the boolean attribute activeState on a wine in the database.
     * Receives the ID as a path variable
     * @param ID
     * @return List<Wine>
     */
    @PostMapping("admin/changeActiveState/{ID}")
    public ResponseEntity<List<Wine>> changeActiveState(@PathVariable("ID") @Pattern(regexp = "^\\d+$") String ID) {
        //The @Pattern annotation validates the input against the regex
        //the input string must only contain digits
        try {
            return ResponseEntity.ok(wineService.changeActiveState(ID)); //creates the response
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build(); //builds a response with status code 500
        }
    }
}
