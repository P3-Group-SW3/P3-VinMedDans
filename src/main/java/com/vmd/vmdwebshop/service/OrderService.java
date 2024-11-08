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

    public List<Orders> getAllOrders() { return orderReporsitory.findAll(); }

    public Orders getOrderById(Long id){ return orderReporsitory.findById(id).orElse(null); }

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

    public void changeState(Long id, int state) {
        Orders order = getOrderById(id);
        order.setState(Orders.State.values()[state]);
    }

}
