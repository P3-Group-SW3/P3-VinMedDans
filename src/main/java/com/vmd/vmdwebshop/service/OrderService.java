package com.vmd.vmdwebshop.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    private OrderRepository orderReporsitory;

    /**
     * Denne classe returnere alle odrene i systemet
     * @return List<Orders>
     */
    public List<Orders> getAllOrders() { return orderReporsitory.findAll(); }

    /**
     * finder en specifik order based on the order id
     * @param orderID
     * @return
     */
    public Orders getOrderById(Long orderID){ return orderReporsitory.findById(orderID).orElse(null); }

    /**
     * Creates a order based on the information given by the customer
     * @param orderinfo
     * @param orderLines
     * @return
     */
    public Orders createOrderfromInfo(Orderinfo orderinfo, List<OrderLine> orderLines) {
        Orders order = orderinfo.createOrderFromInfo();
        for(OrderLine orderLine : orderLines) {
            order.addOrderLine(orderLine);
            orderLine.setOrders(order);
            orderLine.removeCustomerID();
        }
        order.setState(Orders.State.REGISTERED);
        orderReporsitory.save(order);
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
        order.setState(Orders.State.values()[state]);
    }

}
