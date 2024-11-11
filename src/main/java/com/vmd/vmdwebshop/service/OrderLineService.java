package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.orderline.CartNotClearedException;
import com.vmd.vmdwebshop.exception.orderline.EmptyCartException;
import com.vmd.vmdwebshop.exception.orderline.OrderLineDataAccessException;
import com.vmd.vmdwebshop.exception.orderline.OrderLineDoesNotExistException;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    /**
     * createAndEditOrderLine
     * @param orderLine
     * @return List<OrderLine> returns all the orderlines associated with the customer
     * @throws OrderLineDataAccessException if either fails to retrieve the orderlines from the database, or if fails to update/ save
     * the new orderlines to the database.
     */
    public List<OrderLine> createAndEditOrderLine(OrderLine orderLine) {

        OrderLine existingOrderLine;

        try {
            existingOrderLine =
                    orderLineRepository.findByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());
        } catch (DataAccessException e){ throw new OrderLineDataAccessException("Failed to retrieve the orderlines from the database");}

        try {
            if (existingOrderLine != null) {
                existingOrderLine.setAmount(orderLine.getAmount());
            } else if (orderLine.getAmount() == 0 || orderLine.getAmount() <= 0) {
                orderLineRepository.deleteOrderLineByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());
            } else {
                orderLineRepository.save(orderLine);
            }
        } catch (DataAccessException e){ throw new OrderLineDataAccessException("Failed to update or save to the database");}

            return orderLineRepository.findAllByCustomerId(orderLine.getCustomerID());
    }

    /**
     * clearCart
     * This method clears a customer's cart by deleting all orderlines that matches a specific customer id.
     * First finds all orderlines matching the customer id, if no such orderlines exist an exception will be thrown.
     * Then will execute the deletion of the orderlines, and afterward checks if the deletion was successful by checking
     * if any orderlines remain in the cart. If so, an exception error will be thrown.
     * The method will return a list of the remaining orderlines associated with the customer.
     * @param customerID
     * @return List<OrderLine> will return an empty list if the cart clearance was successful
     * @throws EmptyCartException if cart is already empty at initialisation
     * @throws CartNotClearedException if orderlines remain after deletion attempt
     * @throws OrderLineDataAccessException
     */
    public List<OrderLine> clearCart(String customerID) {

        try {
            List<OrderLine> orderLines = orderLineRepository.findAllByCustomerId(customerID);
            if (orderLines.isEmpty()) {
                throw new EmptyCartException("The cart is already empty");
            }

            orderLineRepository.deleteOrderLinesByCustomerId(customerID);
            List<OrderLine> remainingOrderLines = orderLineRepository.findAllByCustomerId(customerID);

            if (!remainingOrderLines.isEmpty()) {
                throw new CartNotClearedException("The cart has not been cleared");
            }

            return remainingOrderLines;

        } catch (DataAccessException e){throw new OrderLineDataAccessException("Database error");}

    }

    public double calculateOrderLine(int amount, double price) {
        return amount * price;
    }

    /**
     * deleteOrderLine
     * This method checks for the existence of a single orderline that matches a specific customer id and wine id.
     * If such an orderline exists, it will be deleted, else an en exception will be thrown.
     * The method will return a list of the remaining orderlines associated with the customer.
     * @param orderLine
     * @return List<OrderLine>
     * @throws NullPointerException if no orderline exists matching the specified customer id and wine id
     */
    public List<OrderLine> deleteOrderLine(OrderLine orderLine) {
        OrderLine existingOrderLine =
                orderLineRepository.findByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());

        if(existingOrderLine == null){
            throw new NullPointerException("No such orderline exists");
        }
        else {
            orderLineRepository.deleteOrderLineByCustomerIDAndWineID(orderLine.getCustomerID(), orderLine.getWineID());
        }
        return orderLineRepository.findAllByCustomerId(orderLine.getCustomerID());

    }
}

