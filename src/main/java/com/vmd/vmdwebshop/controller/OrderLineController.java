package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vmd.vmdwebshop.service.OrderLineService;
import com.vmd.vmdwebshop.model.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/base/")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private WineRepository wineRepository;

    @PostMapping("/api/createOrderLine")
    public List<OrderLine> createOrderLine(@RequestBody Long customer_id, int amount, String wine_id){

        OrderLine orderLine = orderLineService.createOrderLine(customer_id, amount, Long.parseLong(wine_id));
        Wine wine = wineRepository.findById(Long.parseLong(wine_id)).get();
        List<OrderLine> orderLines = orderLineRepository.findByCustomerId(customer_id);

        double orderLinePrice = orderLineService.calculateOrderLine(orderLine.getAmount(), wine.getPrice());

        return orderLines;
    }

    @PostMapping("/api/editOrderLine")
    public void editOrderLine(@RequestBody Long customer_id, int amount, String wine_id){



        orderLineService.editOrderLine(customer_id, amount, Long.parseLong(wine_id));
    }
    //Funktion der tager ID, amoung, WineID.
    //Først finder vi den specifikke orderline på baggrund af customerID først, og bagefter WineID,
    //Så skal vi ændre i amountet på orderlinen


    @PostMapping("/api/clearCart")
    public void clearCart(@RequestBody String customer_id){
        orderLineService.clearCart(Long.parseLong(customer_id));
    }

    @PostMapping("/api/incrementOrderLine")
    public List<OrderLine> modulateOrderLine(@RequestBody String customer_id, String wine_id, boolean increment){



        return orderLineRepository.findByCustomerId(Long.parseLong(customer_id));

    }

}
