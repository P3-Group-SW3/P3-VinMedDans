package com.vmd.vmdwebshop.exception.wine;

public class WineDidNotUpdateDataBaseException extends RuntimeException {
    private String message;

    public WineDidNotUpdateDataBaseException(String message) {
        super(message);
        this.message = message;
    }
}
