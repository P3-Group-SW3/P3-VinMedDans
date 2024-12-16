package com.vmd.vmdwebshop.exception.orderline;

public class OrderLineDoesNotExistException extends RuntimeException {

    public OrderLineDoesNotExistException(String message) {
        super(message);
    }
}
