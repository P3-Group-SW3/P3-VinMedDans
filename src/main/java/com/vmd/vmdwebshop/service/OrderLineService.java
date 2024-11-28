package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.orderline.*;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final WineService wineService;

    // Constructor injection to receive the repositories

    /**
     * Constructor Dependency Injection (CDI) - Dependencies are injected via the constructor.
     * We use a constructor to inject the concepts of the wine and orderline repositories into the OrderLineService class.
     * @param error
     * @param orderLineRepository
     * @param wineRepository
     */
    public OrderLineService(View error, OrderLineRepository orderLineRepository, WineRepository wineRepository, WineService wineService) {
        this.error = error;
        this.orderLineRepository = orderLineRepository;
        this.wineRepository = wineRepository;
        this.wineService = wineService;
    }

    public List<OrderLine> getAllOrderLines(String customerID) {
        try{
            return orderLineRepository.findAllByCustomerId(customerID);
        } catch (DataAccessException e) {
        throw new OrderLineDataAccessException(" Can not access the database");}
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
                orderLineRepository.save(existingOrderLine);

            } else {
                orderLine.setWine(wineService.getWineById(orderLine.getWineID()));// Gives orderline access to the wine object
                orderLine.setAmount(orderLine.getAmount());
                orderLineRepository.save(orderLine);

            }
            if (orderLine.getAmount() < 1) {
                throw new IllegalArgumentException("The amount cannot be less than 1!");
            }



        } catch (DataIntegrityViolationException e){
            throw new OrderLineNotUpdatedException("Failed to update the orderline");
        } catch (DataAccessException e){
            throw new OrderLineDataAccessException("Failed to save orderline to the database");}

            return orderLineRepository.findAllByCustomerId(orderLine.getCustomerID());
    }

    /**
     * clearCar
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

        } catch (DataAccessException e) {
            throw new OrderLineDataAccessException(" Can not access the database");
        }
    }

    public Double calculateOrderLine(OrderLine orderLine) {

        return orderLine.getAmount() * orderLine.getWine().getPrice();
    }

    public double calculateOrderLine(List<OrderLine> orderLines) {
        double totalPrice = 0.0;
        for (OrderLine orderLine: orderLines){
            Double price = calculateOrderLine(orderLine);
            totalPrice += price;
        }
        return totalPrice;
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

    public boolean canBePurchased(List<OrderLine> orderLineList){
        boolean canBePurchased = true;
        String exceptionMessage = "There is not enough stock for wine(s):";

        try{
            for(OrderLine orderLine : orderLineList) {
                Wine wine = wineRepository.getById(orderLine.getWineID());
                if (!wine.canBePurchased(orderLine.getAmount())){
                    canBePurchased = false;
                    exceptionMessage += " ID:" + wine.getID();
                }
            }
        }catch (DataAccessException e){
            System.out.println(e.getMessage());
        }



        if (canBePurchased == false){
            throw new OrderLineCannotBePurchased(exceptionMessage);
        }

        return canBePurchased;
    }
}

