package com.vmd.vmdwebshop.exception.orderline;

public class CartNotClearedException extends RuntimeException{

    private String message;

    public CartNotClearedException(String message){
        super(message);
        this.message = message;
    }
}
