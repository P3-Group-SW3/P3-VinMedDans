package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vmd.vmdwebshop.service.OrderLineService;
import com.vmd.vmdwebshop.model.*;
import com.vmd.vmdwebshop.model.Wine;

import java.util.List;

@RestController
@RequestMapping("/")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private WineRepository wineRepository;


    //Funktion der tager ID, amoung, WineID.
    //Først finder vi den specifikke orderline på baggrund af customerID først, og bagefter WineID,
    //Så skal vi ændre i amountet på orderlinen


    @DeleteMapping("/api/clearCart/{id}")
    public void clearCart(@PathVariable String customer_ID){
        orderLineService.clearCart(Long.parseLong(customer_ID));
    }
}
