package com.vmd.vmdwebshop.exception.orderline;

public class OrderLineDoesNotExistException extends RuntimeException {

    private String message;

    public OrderLineDoesNotExistException(String msg){
        super(msg);
        this.message = msg;
    }
}
