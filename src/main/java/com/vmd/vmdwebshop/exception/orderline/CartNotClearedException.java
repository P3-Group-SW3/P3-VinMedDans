package com.vmd.vmdwebshop.exception.orderline;

public class CartNotClearedException extends RuntimeException{

    private String message;

    public CartNotClearedException(String msg){
        super(msg);
        this.message = msg;
    }
}
