package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.exception.order.OrderNotFoundInDatbase;
import com.vmd.vmdwebshop.exception.order.OrderlineNotAdded;
import com.vmd.vmdwebshop.exception.order.StateChangeFailedException;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Orders;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vmd.vmdwebshop.service.*;


import java.util.List;

@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    private OrderLineService orderLineService;

    /**
     * This is the post mapping from the request from the front end and makes an order from a customer.
     *
     * @param order
     * @param customerID
     * We take theese two values, the order is filled with information based on the frontend
     * And we use the cookie id to get the list of ordelines from the customer send these objects through our order service
     * @return
     */
    @PostMapping("/api/orderInfo/{customerID}")

    public ResponseEntity<Orders> createOrder(@Valid @RequestBody Orderinfo order, @PathVariable String customerID) {

        try{
            List<OrderLine> orderLines = orderLineService.getAllOrderLines(customerID);
            Orders orders = orderService.createOrderfromInfo(order, orderLines);
            return ResponseEntity.ok(orders);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Gets all orders
     * @return
     */
    @GetMapping("/api/orders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Gets a specifiv order based on id
     * @param orderID
     * @return
     */
    @GetMapping("/api/orders/{orderID}")
    public ResponseEntity<Orders> getOrderById(@PathVariable String orderID) {
        try {
            Orders order = orderService.getOrderById(Long.parseLong(orderID));
            return ResponseEntity.ok(order);
        }
        catch (OrderNotFoundInDatbase e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * changes the state of an order based on a number from 0 to 2
     * @param orderID
     * @param state
     */
    @PostMapping("/api/orders/state/{orderID}")
    public void changeState(@PathVariable Long orderID, @RequestBody OrderState state) {
        //når vi laver denne skal vi senere gemme ændringerne
        try {
            orderService.changeState(orderID, state.getState());
        }catch (StateChangeFailedException e){
            System.out.println(e.getMessage());
        }
    }
}
