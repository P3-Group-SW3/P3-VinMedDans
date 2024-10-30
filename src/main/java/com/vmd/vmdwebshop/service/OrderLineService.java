package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Customer;
import org.springframework.stereotype.Service;
import com.vmd.vmdwebshop.model.*;
import com.vmd.vmdwebshop.repository.*;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderLineService {

    private final View error;
    private WineRepository wineRepository;
    private OrderLineRepository orderLineRepository;

    public OrderLineService(View error) {
        this.error = error;
    }

    public Optional<OrderLine> getCustomersOrderLines(Long customer_id) {
        return orderLineRepository.findById(customer_id);}

    public void createOrderLine(Long customer_id, int amount, Long wine_id) {

        Wine wine = wineRepository.findById(wine_id);

        OrderLine orderLine = new OrderLine(customer_id, amount, wine);

        orderLineRepository.save(orderLine);
    }

    // Looks for an orderline that fits both customer id and wine id
    public void editOrderLine(Long customer_id, int amount, Long wine_id){
        OrderLine orderLine = orderLineRepository.findByCustomerIdAndWineId(customer_id, wine_id);
        if(orderLine != null){
            orderLine.editOrderLineAmount(addAmount);
        }
        else throw error
    }

    public double calculateOrderLine(int amount, double price){
        return amount * price;
    }



}
