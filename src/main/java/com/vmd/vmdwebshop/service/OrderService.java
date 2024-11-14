package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.order.*;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.vmd.vmdwebshop.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;
import com.vmd.vmdwebshop.model.*;

import java.util.*;


@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    private final View error;

    // fjern / tilføj OrderRepository orderRepository baseret på test
    public OrderService(View error){
        this.error = error;
        //this.orderRepository = orderRepository;
    }

    /**
     * Denne classe returnere alle odrene i systemet
     * @return List<Orders>
     */
    public List<Orders> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        if(orders.isEmpty() || orders == Null){
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
     * @param orderinfo
     * @param orderLines
     * @return
     */
    public Orders createOrderfromInfo(Orderinfo orderinfo, List<OrderLine> orderLines) {
        Orders order = orderinfo.createOrderFromInfo();
        order.setState(Orders.State.REGISTERED);
        orderRepository.save(order);
        if(order.getID() == null){
            throw new OrderNotSaved(order.getFullName(), order.getMail());
        }
        for(OrderLine orderLine : orderLines) {
            order.addOrderLine(orderLine);
            orderLine.setOrders(order);
            orderLine.removeCustomerID();
            if(orderLine.GetorderID() != order.getID()){
                throw new OrderlineNotAdded("orderline with id: " + orderLine.getID() + " did not add the order ID of:" + order.getID());
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
        Orders.State state2 = Orders.State.values()[state];
        order.setState(Orders.State.values()[state]);
        if(order.getState() != state2) {
            throw new StateChangeFailedException(state1, state2);
        }
    }

}
