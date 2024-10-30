package com.vmd.vmdwebshop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vmd.vmdwebshop.service.OrderLineService;
import com.vmd.vmdwebshop.model.*;

@RestController
@RequestMapping("/base/")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;

    @PostMapping("/api/createOrderLine")
    public void createOrderLine(@RequestBody Long customer_id, int amount, String wine_id){

        orderLineService.createOrderLine(customer_id, amount, Long.parseLong(wine_id));
    }

    @PostMapping("/api/calculateOrderLine")
    public double calculateOrderLine(@RequestBody int amount, double price) {
        orderLineService.calculateOrderLine(amount, price);
        return orderLineService.calculateOrderLine(amount, price);
    }


    @PostMapping("/api/editOrderLine")
    public void editOrderLine(@RequestBody Long customer_id, int amount, String wine_id){
        orderLineService.editOrderLine(customer_id, amount, Long.parseLong(wine_id));
    }
    //Funktion der tager ID, amoung, WineID.
    //Først finder vi den specifikke orderline på baggrund af customerID først, og bagefter WineID,
    //Så skal vi ændre i amountet på orderlinen
}
