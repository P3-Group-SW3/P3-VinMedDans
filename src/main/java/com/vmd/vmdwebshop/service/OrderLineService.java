package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.orderline.CartNotClearedException;
import com.vmd.vmdwebshop.exception.orderline.OrderLineDoesNotExistException;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;


import com.vmd.vmdwebshop.repository.*;
import com.vmd.vmdwebshop.model.*;
import java.util.List;
import java.util.*;

@Service
@Transactional
public class OrderLineService {


    private final View error;

    private final WineRepository wineRepository;
    private final OrderLineRepository orderLineRepository;

    // Constructor injection to receive the repositories

    /**
     * Constructor Dependency Injection (CDI) - Dependencies are injected via the constructor.
     * We use a constructor to inject the concepts of the wine and orderline repositories into the OrderLineService class.
     * @param error
     * @param orderLineRepository
     * @param wineRepository
     */
    public OrderLineService(View error, OrderLineRepository orderLineRepository, WineRepository wineRepository) {
        this.error = error;
        this.orderLineRepository = orderLineRepository;
        this.wineRepository = wineRepository;
    }

    public List<OrderLine> getAllOrderLines(String customerID) {
        return orderLineRepository.findAllByCustomerId(customerID);
    }


    public List<OrderLine> createAndEditOrderLine(OrderLine orderLine) {

        OrderLine existingOrderLine = orderLineRepository.findByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());

        if (existingOrderLine != null) {
            existingOrderLine.setAmount(orderLine.getAmount());
        } else if (orderLine.getAmount() == 0 || orderLine.getAmount() <= 0) {
            orderLineRepository.deleteOrderLineByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());
        } else {
            orderLineRepository.save(orderLine);
        }
        return orderLineRepository.findAllByCustomerId(orderLine.getCustomerID());
    }

    public List<OrderLine> clearCart(String customerID) {
        orderLineRepository.deleteOrderLinesByCustomerId(customerID);

        List<OrderLine> orderLines = orderLineRepository.findAllByCustomerId(customerID);
        if (!orderLines.isEmpty()){
            throw new CartNotClearedException("The cart has not been cleared");
        }
        else{
            return orderLines;
        }
    }

    public double calculateOrderLine(int amount, double price) {
        return amount * price;
    }


    public List<OrderLine> deleteOrderLine(OrderLine orderLine) {
        OrderLine existingOrderLine =
                orderLineRepository.findByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());

        if(existingOrderLine == null){
            throw new OrderLineDoesNotExistException("No such orderline exists");
        }
        else {
            orderLineRepository.deleteOrderLineByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());
        }
        return orderLineRepository.findAllByCustomerId(orderLine.getCustomerID());

    }
}

