package com.vmd.vmdwebshop.exception.orderline;

public class OrderLineDataAccessException extends RuntimeException{

    private String message;

    public OrderLineDataAccessException(String msg){
        super(msg);
        this.message = msg;
    }
}
