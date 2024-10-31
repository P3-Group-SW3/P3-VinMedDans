package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base/")
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping("/api/wine")
    public List<Wine> getAll(){
        return wineService.getAllWines();
    }
}
