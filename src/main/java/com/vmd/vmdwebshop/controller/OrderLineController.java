package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vmd.vmdwebshop.service.OrderLineService;
import com.vmd.vmdwebshop.model.*;
import com.vmd.vmdwebshop.model.Wine;

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

    @PostMapping(value ="/api/createOrderLine", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<OrderLine>> createOrderLine(@RequestBody Long customer_ID, int amount, String wine_ID){

        OrderLine orderLine = orderLineService.createOrderLine(customer_ID, amount, Long.parseLong(wine_ID));
        Wine wine = wineRepository.findById(Long.parseLong(wine_ID)).get();
        List<OrderLine> orderLines = orderLineRepository.findByCustomerId(customer_ID);

        double orderLinePrice = orderLineService.calculateOrderLine(orderLine.getAmount(), wine.getPrice());

        return ResponseEntity.ok(orderLines);
    }

    @PostMapping("/api/editOrderLine")
    public ResponseEntity<List<OrderLine>> editOrderLine(@RequestBody Long customer_ID, int amount, String wine_ID){

        orderLineService.editOrderLine(customer_ID, amount, Long.parseLong(wine_ID));

        return ResponseEntity.ok(orderLineRepository.findByCustomerId(customer_ID));
    }
    //Funktion der tager ID, amoung, WineID.
    //Først finder vi den specifikke orderline på baggrund af customerID først, og bagefter WineID,
    //Så skal vi ændre i amountet på orderlinen


    @DeleteMapping("/api/clearCart/{id}")
    public void clearCart(@PathVariable String customer_ID){
        orderLineService.clearCart(Long.parseLong(customer_ID));
    }

    @PostMapping("/api/incrementOrderLine")
    public ResponseEntity<List<OrderLine>> modulateOrderLine(@RequestBody String customer_ID, String wine_ID, boolean increment){


        return ResponseEntity.ok(orderLineRepository.findByCustomerId(Long.parseLong(customer_ID)));

    }
}
