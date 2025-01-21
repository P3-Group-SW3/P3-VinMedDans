package com.vmd.vmdwebshop.exception.orderline;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }
}
