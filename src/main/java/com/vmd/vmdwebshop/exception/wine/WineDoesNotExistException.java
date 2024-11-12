package com.vmd.vmdwebshop.exception.wine;

public class WineDoesNotExistException extends RuntimeException {
    private String message;

    public WineDoesNotExistException(String message) {
        super(message);
        this.message = message;
    }
}
