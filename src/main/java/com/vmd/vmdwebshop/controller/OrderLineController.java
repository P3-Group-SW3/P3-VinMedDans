package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import com.vmd.vmdwebshop.service.CustomerService;
import com.vmd.vmdwebshop.service.OrderLineService;
import com.vmd.vmdwebshop.service.WineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private WineService wineService;
    @Autowired
    private CustomerService customerService;


    /**
     * this get request takes the customer id as a pathvariable, and returns the users orderlines
     * returns their "cart"
     * @param request
     * @return list of orderlines
     */
    @GetMapping("/api/getAllOrderLines")
    public ResponseEntity<List<OrderLine>> getAllOrderLines(HttpServletRequest request) {
        try {
            String customerID = customerService.getCustomerID(request);
            return ResponseEntity.ok(orderLineService.getAllOrderLines(customerID));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * this get request takes the customer id as a pathvariable, and returns the users orderlines
     * returns their "cart"
     * @param request
     * @return list of orderlines
     */
    @GetMapping("/api/getPrice")
    public ResponseEntity<Double> getPrice(HttpServletRequest request) {
        try {
            String customerID = customerService.getCustomerID(request);
            List<OrderLine> orderLines = orderLineService.getAllOrderLines(customerID);
            Double price = orderLineService.calculateOrderLines(orderLines);
            return ResponseEntity.ok(price);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<List<OrderLine>> createOrderLine(@RequestBody @Valid OrderLine orderLine, HttpServletRequest request) {
        try {
            String customerID = customerService.getCustomerID(request);
            orderLine.setCustomerID(customerID);
            return ResponseEntity.ok(orderLineService.createAndEditOrderLine(orderLine));
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }


    }

    @GetMapping("/api/clearCart/")
    public ResponseEntity<List<OrderLine>> clearCart(HttpServletRequest request) {
        try {
            String customerID = customerService.getCustomerID(request);
            return ResponseEntity.ok(orderLineService.clearCart(customerID));
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("api/returnOrderLine")
    public ResponseEntity<OrderLine> returnOrderLine(@RequestBody OrderLine orderLine, HttpServletRequest request) {

        String customerID = customerService.getCustomerID(request);

        orderLine.setCustomerID(customerID);
        orderLine.setWine(wineService.getWineById(orderLine.getWineID()));

        return ResponseEntity.ok(orderLine);
    }

    @PostMapping("api/deleteOrderLine")
    public ResponseEntity<List<OrderLine>> deleteOrderLine(@RequestBody OrderLine orderLine, HttpServletRequest request) {

        try{
            String customerID = customerService.getCustomerID(request);
            orderLine.setCustomerID(customerID);

            return ResponseEntity.ok(orderLineService.deleteOrderLine(orderLine));
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }



}

