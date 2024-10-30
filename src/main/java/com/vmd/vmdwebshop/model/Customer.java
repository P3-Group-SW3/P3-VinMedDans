package com.vmd.vmdwebshop.model;

public class Customer extends User{

    //Instance variables
    private boolean legalAge;

    //Constructors
    public Customer() {}

    public Customer(String id) {
        super(id);
        this.legalAge = false;
    }

    //Getters
    public boolean getLegalAge() { return this.legalAge; }

    //Setters
    public void setLegalAge(boolean legalAge) { this.legalAge = legalAge; }


}
