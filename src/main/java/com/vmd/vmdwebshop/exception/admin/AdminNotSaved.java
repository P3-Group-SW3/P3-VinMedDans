package com.vmd.vmdwebshop.exception.admin;

public class AdminNotSaved extends RuntimeException {
    private String name;
    private String password;

    // Constructor
    public AdminNotSaved(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getMessage(){
        return "The order was not created, customer: " + name + " password: " + password;
    }
}
