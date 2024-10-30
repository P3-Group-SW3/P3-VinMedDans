package com.vmd.vmdwebshop.model;

public abstract class User {

    //Instance variables
    private String id;

    //Constructors
    public User() {}

    public User(String id) {
        this.id = id;
    }

    //Getters
    public String getId() { return this.id; }

    //Setters
    public void setId(String id) { this.id = id; }
}
