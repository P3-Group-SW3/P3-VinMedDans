package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Orders;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.service.OrderLineService;
import com.vmd.vmdwebshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vmd.vmdwebshop.service.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    private OrderLineRepository orderLineRepository;

    /**
     * This is the post mapping from the request from the front end and makes an order from a customer.
     *
     * @param order
     * @param customerID
     * We take theese two values, the order is filled with information based on the frontend
     * And we use the cookie id to get the list of ordelines from the customer send these objects through our order service
     * @return
     */
    @PostMapping("/api/orderInfo")

    public ResponseEntity<Orders> createOrder(@RequestBody Orderinfo order, @CookieValue(value = "cookieId", defaultValue = "") String customerID) {
        List<OrderLine> orderLines = orderLineRepository.findAllByCustomerId(customerID);
        return ResponseEntity.ok(orderService.createOrderfromInfo(order, orderLines));
    }

    /**
     * Gets all orders
     * @return
     */
    @GetMapping("/api/orders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Gets a specifiv order based on id
     * @param orderID
     * @return
     */
    @GetMapping("/api/orders/{orderID}")
    public ResponseEntity<Orders> getOrderById(@PathVariable String orderID) {
        Orders order = orderService.getOrderById(Long.parseLong(orderID));
        if(order!= null){
            return ResponseEntity.ok(order);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * changes the state of an order
     * @param orderID
     * @param state
     */
    @PostMapping("/api/orders/state/{orderID}")
    public void changeState(@PathVariable Long orderID, @RequestParam int state) {
        //når vi laver denne skal vi senere gemme ændringerne 
        orderService.changeState(orderID, state);
    }
}
