package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Distributor;
import com.vmd.vmdwebshop.service.DistributorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributor")
public class DistributorController {

    private final DistributorService distributorService;

    // Constructor
    @Autowired
    public DistributorController(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @GetMapping("/getList")
    public ResponseEntity<List<Distributor>>  getAll() {
        try {
            return ResponseEntity.ok(distributorService.getAll());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/createAndEdit")
    public ResponseEntity<List<Distributor>> createAndEdit(@RequestBody @Valid Distributor distributor) {
        try {
            return ResponseEntity.ok(distributorService.createAndEdit(distributor, distributor.getID()));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/admin/delete/{ID}")
    public ResponseEntity<List<Distributor>> delete(@PathVariable ("ID") Long ID) {
        try {
            return ResponseEntity.ok(distributorService.delete(ID));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }




}
