package com.vmd.vmdwebshop.exception.order;

import com.vmd.vmdwebshop.model.Orders;

public class OrderNotSaved extends RuntimeException {
    private String name;
    private String email;
    public OrderNotSaved(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String getMessage(){
        return "The order was not created, customer: " + name + " email: " + email;
    }
}
