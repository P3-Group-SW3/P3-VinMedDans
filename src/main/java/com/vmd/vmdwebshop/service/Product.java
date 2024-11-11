package com.vmd.vmdwebshop.service;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private double price;
    private String description;
    private String imageURL;

    public Product() {}

    public Product(Long ID, double price, String description, String imageURL) {
        this.ID = ID;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
    }

    //Getters
    public String getDescription() { return this.description; }

    public String getImageURL() { return this.imageURL; }

    public double getPrice() { return this.price; }

    public Long getID() { return this.ID; }


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
