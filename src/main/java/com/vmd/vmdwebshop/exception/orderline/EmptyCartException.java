package com.vmd.vmdwebshop.exception.orderline;

public class EmptyCartException extends RuntimeException{
    private String message;

    public EmptyCartException(String message){
        super(message);
        this.message = message;
    }
}
