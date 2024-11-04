package com.vmd.vmdwebshop.service;

import org.springframework.stereotype.Service;
import com.vmd.vmdwebshop.repository.*;
import org.springframework.web.servlet.View;
import com.vmd.vmdwebshop.model.*;

import java.util.*;


@Service
public class OrderLineService {

    private final View error;
    private WineRepository wineRepository;
    private OrderLineRepository orderLineRepository;

    public OrderLineService(View error) {
        this.error = error;
    }

    public OrderLine createOrderLine(Long customer_ID, int amount, Long wine_ID) {
        double price;

        Wine wine = wineRepository.findById(wine_ID).orElse(null);
        System.out.println("her");
        OrderLine orderLine = orderLineRepository.findById(customer_ID).orElse(null);
        System.out.println("her2");

        if(wine != null && orderLine != null) {
            orderLine = new OrderLine(customer_ID, amount, wine, customer_ID);
            orderLineRepository.save(orderLine);
            price = orderLine.getAmount() * wine.getPrice();

        } else {
            System.out.println("Der er sket en fejl");
        }

        return orderLine;
    }

    // Looks for an orderline that fits both customer id and wine id
    public void editOrderLine(Long customer_ID, int amount, Long wine_ID){

        OrderLine orderLine = orderLineRepository.findByCustomerIDAndWineID(customer_ID, wine_ID);

        if(orderLine != null){
            orderLine.setAmount(amount);

            List<OrderLine> orderLineList;
            orderLineList = orderLineRepository.findByCustomerId(customer_ID);
        } else {
            System.out.println("Der er sket en fejl");
        }


    }

    public void clearCart(Long customer_ID){
        orderLineRepository.deleteOrderLinesByCustomerId(customer_ID);

        OrderLine orderLine = orderLineRepository.findById(customer_ID).orElse(null);

        if(orderLine != null){
            System.out.println("Succes");
        } else {
            System.out.println("Failed");
        }

    }

    public double calculateOrderLine(int amount, double price){
        return amount * price;
    }

    public void modulateOrderLine(Long customer_ID, Long wine_ID, boolean increment){

        Wine wine = wineRepository.findById(wine_ID).orElse(null);

        OrderLine orderLine = orderLineRepository.findByCustomerIDAndWineID(customer_ID, wine_ID);

    }

    //lav en remove orderline



}
