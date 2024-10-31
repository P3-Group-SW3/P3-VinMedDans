package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/api/createOrderLine")
    public List<OrderLine> createOrderLine(@RequestBody Long customer_ID, int amount, String wine_ID){

        OrderLine orderLine = orderLineService.createOrderLine(customer_ID, amount, Long.parseLong(wine_ID));
        Wine wine = wineRepository.findById(Long.parseLong(wine_ID)).get();
        List<OrderLine> orderLines = orderLineRepository.findByCustomerId(customer_ID);

        double orderLinePrice = orderLineService.calculateOrderLine(orderLine.getAmount(), wine.getPrice());

        return orderLines;
    }

    @PostMapping("/api/editOrderLine")
    public void editOrderLine(@RequestBody Long customer_ID, int amount, String wine_ID){



        orderLineService.editOrderLine(customer_ID, amount, Long.parseLong(wine_ID));
    }
    //Funktion der tager ID, amoung, WineID.
    //Først finder vi den specifikke orderline på baggrund af customerID først, og bagefter WineID,
    //Så skal vi ændre i amountet på orderlinen


    @PostMapping("/api/clearCart")
    public void clearCart(@RequestBody String customer_ID){
        orderLineService.clearCart(Long.parseLong(customer_ID));
    }

    @PostMapping("/api/incrementOrderLine")
    public List<OrderLine> modulateOrderLine(@RequestBody String customer_ID, String wine_ID, boolean increment){



        return orderLineRepository.findByCustomerId(Long.parseLong(customer_ID));

    }

}
