package com.vmd.vmdwebshop.exception.orderline;

public class OrderLineDoesNotExistException extends RuntimeException {

    private String message;

    public OrderLineDoesNotExistException(String message){
        super(message);
        this.message = message;
    }
}
