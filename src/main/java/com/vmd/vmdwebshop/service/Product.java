package com.vmd.vmdwebshop.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@MappedSuperclass
public abstract class Product {

    @Digits(integer = 5, fraction = 0, message = "The price can't be higher than 99999")
    @Min(1)
    private double price;

    @NotBlank(message = "this field must not be empty")
    private String description;

    @NotBlank(message = "this field must not be empty")
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
