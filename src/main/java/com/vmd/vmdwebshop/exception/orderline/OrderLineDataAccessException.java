package com.vmd.vmdwebshop.exception.orderline;

public class OrderLineDataAccessException extends RuntimeException{

    private String message;

    public OrderLineDataAccessException(String message){
        super(message);
        this.message = message;
    }
}
