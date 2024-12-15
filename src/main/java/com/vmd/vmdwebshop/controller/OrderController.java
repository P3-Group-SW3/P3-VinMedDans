package com.vmd.vmdwebshop.controller;


import com.vmd.vmdwebshop.DTO.OrderDto;
import com.vmd.vmdwebshop.DTO.OrderStateDTO;
import com.vmd.vmdwebshop.exception.order.OrderNotFoundInDatbase;
import com.vmd.vmdwebshop.exception.order.StateChangeFailedException;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Orders;
import com.vmd.vmdwebshop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
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
    @Autowired
    private CustomerService customerService;

    /**
     * This is the post mapping from the request from the front end and makes an order from a customer.
     *
     * @param order
     * @param request
     * We take theese two values, the order is filled with information based on the frontend
     * And we use the cookie id to get the list of ordelines from the customer send these objects through our order service
     * @return
     */
    @PostMapping("/api/")
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody OrderDto order, HttpServletRequest request) {

        try{
            String customerID = customerService.getCustomerID(request);
            List<OrderLine> orderLines = orderLineService.getAllOrderLines(customerID);
            Orders orders = orderService.createOrderFromInfo(order, orderLines, null);
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
    @GetMapping("/api/orders/getList")
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
    public ResponseEntity<Orders> getOrderById(@PathVariable @Pattern(regexp = "^\\d+$") String orderID) {
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
    public void changeState(@PathVariable @Pattern(regexp = "^\\d+$") String orderID, @RequestBody OrderStateDTO state) {
        //når vi laver denne skal vi senere gemme ændringerne
        try {
            orderService.changeState(Long.parseLong(orderID), state.getState());
        }catch (StateChangeFailedException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get Order from a customer using sessionID
     * @param session_id
     * @return
     */
    @GetMapping("/api/orders/session/{session_id}")
    public ResponseEntity<Orders> getOrderBySessionID(@PathVariable String session_id) {
        try {
            System.out.println(session_id);
            Orders order = orderService.getOrderBySessionID(session_id);
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundInDatbase e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Gets the state of an order based on session_id
     * @param session_id
     * @return the state of the order
     */
    @GetMapping("/api/orders/state/session/{session_id}")
    public ResponseEntity<Integer> getStateBySessionID(@PathVariable String session_id) {
        try {
            Orders order = orderService.getOrderBySessionID(session_id);
            return ResponseEntity.ok(order.getState().ordinal());
        } catch (OrderNotFoundInDatbase e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/orders/admin/delete/{orderID}")
    public void deleteOrder(@PathVariable Long orderID){
        System.out.println(orderID);
        try {
            Orders order = orderService.getOrderById(orderID);
            orderService.deleteOrder(order);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}
