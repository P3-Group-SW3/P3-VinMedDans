package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.order.*;
import org.springframework.stereotype.Service;
import com.vmd.vmdwebshop.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;
import com.vmd.vmdwebshop.model.*;

import java.util.*;


@Service
@Transactional
public class OrderService {

    private final View error;
   
    private final OrderLineRepository orderLineRepository;

    private final OrderRepository orderRepository;

    // fjern / tilføj OrderRepository orderRepository baseret på test
    public OrderService(View error, OrderRepository orderRepository, OrderLineRepository orderLineRepository){
        this.error = error;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
    }

    /**
     * Denne classe returnere alle odrene i systemet
     * @return List<Orders>
     */
    public List<Orders> getAllOrders() {

        List<Orders> orders = orderRepository.findAll();
        if (orders.isEmpty()){
            throw new OrdersNotFound();
        }
        return orders;
    }

    /**
     * finder en specifik order based on the order id
     * @param orderID
     * @return
     */
    public Orders getOrderById(Long orderID){
        Orders order = orderRepository.findById(orderID).orElse(null);

        if(order == null){
            throw new OrderNotFoundInDatbase(orderID);
        }

        return order;
    }

    /**
     * Creates a order based on the information given by the customer
     * @param orderDto
     * @param orderLines
     * @return
     */
    public Orders createOrderFromInfo(OrderDto orderDto, List<OrderLine> orderLines) {
        Orders order = orderDto.createOrderFromInfo();
        order.setState(Orders.State.REGISTERED);
        order.setDate(new Date());
        orderRepository.save(order);

        if(order.getID() == null){
            throw new OrderNotSaved(order.getFullName(), order.getMail());
        }

        for(OrderLine orderLine : orderLines) {
            order.addOrderLine(orderLine);
            orderLine.setOrders(order);
            if(orderLine.getOrderID() != order.getID()){
                throw new OrderlineNotAdded("orderline with id: " + orderLine.getID() + " did not add the order ID of: " + order.getID());
            }
        }
        return order;
    }

    /**
     * allows admins to change the state of an order
     * Det er her vi ville tilføje emails
     * @param orderID
     * @param state
     */
    public void changeState(Long orderID, int state) {
        Orders order = getOrderById(orderID);
        Orders.State state1 = order.getState();
        Orders.State newState = Orders.State.values()[state];
        order.setState(newState);

        if(order.getState() != newState) {
            throw new StateChangeFailedException(state1, newState);
        }
    }

}
