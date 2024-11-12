package com.vmd.vmdwebshop.exception.order;

public class OrderlineNotAdded extends RuntimeException {
    String messege;
    public OrderlineNotAdded(String message) {
        super(message);
        this.messege = message;
    }
}
