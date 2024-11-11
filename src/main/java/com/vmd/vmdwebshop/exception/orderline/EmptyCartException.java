package com.vmd.vmdwebshop.exception.orderline;

public class EmptyCartException extends RuntimeException{
    private String message;

    public EmptyCartException(String msg){
        super(msg);
        this.message = msg;
    }
}
