package com.vmd.vmdwebshop.exception.order;

public class StateChangefailedException extends RuntimeException {
    String messege;
    public StateChangefailedException(String message) {
        super(message);
        this.messege = message;
    }
}
