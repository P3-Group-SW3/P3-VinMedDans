package com.vmd.vmdwebshop.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@MappedSuperclass
public abstract class Product {

    private double price;

    private String description;

    private String imageURL;

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



    void setPrice(int price) {
        this.price = price;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
