package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import com.vmd.vmdwebshop.service.WineService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vmd.vmdwebshop.service.OrderLineService;
import com.vmd.vmdwebshop.model.*;
import com.vmd.vmdwebshop.model.Wine;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.function.LongFunction;

@RestController
@RequestMapping("/")
public class OrderLineController {

    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private WineRepository wineRepository;
    @Autowired
    private WineService wineService;


    /**
     * this get request takes the customer id as a pathvariable, and returns the users orderlines
     * returns their "cart"
     * @param customerID
     * @return list of orderlines
     */
    @GetMapping("/api/getAllOrderLines/{customerID}")
    public ResponseEntity<List<OrderLine>> getAllOrderLines(@PathVariable Long customerID) {
        return ResponseEntity.ok(orderLineService.getAllOrderLines(customerID));
    }

    /**
     *This function takes a orderline as an object.
     * First it checks whether an orderline exists
     * If it does, i edits the amount
     * If not, it creates and saves a new orderline in the database.
     * It returns all orderlines
     * @param orderLine
     * @return List<OrderLine>
     */
    @PostMapping("/api/createAndEditOrderLine")
    public ResponseEntity<List<OrderLine>> createOrderLine(@RequestBody OrderLine orderLine) {
        return ResponseEntity.ok(orderLineService.createAndEditOrderLine(orderLine));
    }


    @GetMapping("/api/clearCart/{customerID}")
    public ResponseEntity<String> clearCart(@PathVariable Long customerID) {
        if (orderLineService.clearCart(customerID)) {
            return ResponseEntity.ok("Order line has been cleared");
        }

        return ResponseEntity.ok("Order line has not been cleared");
    }

    @PostMapping("api/returnOrderLine")
    public ResponseEntity<OrderLine> returnOrderLine(@RequestBody OrderLine orderLine, @CookieValue(value = "cookieId", defaultValue = "") String cookieID) {

        orderLine.setCustomerID(Long.parseLong(cookieID));

        //orderLineRepository.save(orderLine);

        return ResponseEntity.ok(orderLine);
    }

    @PostMapping("api/deleteOrderLine")
    public ResponseEntity<List<OrderLine>> deleteOrderLine(@RequestBody OrderLine orderLine) {

        return ResponseEntity.ok(orderLineService.deleteOrderLine(orderLine.getWineID(), orderLine.getCustomerID()));
    }



}

