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


    //lav en remove orderline



}
