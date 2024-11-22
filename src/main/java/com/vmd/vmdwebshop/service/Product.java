package com.vmd.vmdwebshop.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@MappedSuperclass
public abstract class Product {

    private double price;

    private String description;

    private String imageURL;

    private boolean activeState = true;

    public Product() {}

    public Product(double price, String description, String imageURL) {
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
    }

    //Getters
    public String getDescription() { return this.description; }

    public String getImageURL() { return this.imageURL; }

    public double getPrice() { return this.price; }

    public boolean getActiveState(){return this.activeState; }



    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void changeActiveState(){ activeState = !activeState; }

}
