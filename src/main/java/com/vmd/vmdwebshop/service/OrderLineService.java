package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Customer;
import org.springframework.stereotype.Service;
import com.vmd.vmdwebshop.model.*;
import com.vmd.vmdwebshop.repository.*;
import org.springframework.web.servlet.View;

import java.util.*;
import java.util.Scanner;


@Service
public class OrderLineService {

    private final View error;
    private WineRepository wineRepository;
    private OrderLineRepository orderLineRepository;
    private CustomerRepository customerRepository;

    public OrderLineService(View error) {
        this.error = error;
    }

    public OrderLine createOrderLine(Long customer_id, int amount, Long wine_id) {
        double price;

        Wine wine = wineRepository.findById(wine_id).orElse(null);
        OrderLine orderLine = orderLineRepository.findById(customer_id).orElse(null);
        Customer customer = customerRepository.findById(customer_id).orElse(null);

        if(wine != null && orderLine != null && customer != null) {
            orderLine = new OrderLine(customer_id, amount, wine, customer);
            orderLineRepository.save(orderLine);
            price = orderLine.getAmount() * wine.getPrice();

        } else {
            System.out.println("Der er sket en fejl");
        }

        return orderLine;
    }

    // Looks for an orderline that fits both customer id and wine id
    public void editOrderLine(Long customer_id, int amount, Long wine_id){

        OrderLine orderLine = orderLineRepository.findByCustomerIdAndWineId(customer_id, wine_id);

        if(orderLine != null){
            orderLine.setAmount(amount);

            List<OrderLine> orderLineList;
            orderLineList = orderLineRepository.findByCustomerId(customer_id);
        } else {
            System.out.println("Der er sket en fejl");
        }


    }

    public void clearCart(Long customer_id){
        orderLineRepository.deleteOrderLinesByCustomerId(customer_id);

        OrderLine orderLine = orderLineRepository.findById(customer_id).orElse(null);

        if(orderLine != null){
            System.out.println("Succes");
        } else {
            System.out.println("Failed");
        }

    }

    public double calculateOrderLine(int amount, double price){
        return amount * price;
    }

    public void modulateOrderLine(Long customer_id, Long wine_id, boolean increment){

        Wine wine = wineRepository.findById(wine_id).orElse(null);

        OrderLine orderLine = orderLineRepository.findByCustomerIdAndWineId(customer_id, wine_id);

    }

    //lav en remove orderline



}
