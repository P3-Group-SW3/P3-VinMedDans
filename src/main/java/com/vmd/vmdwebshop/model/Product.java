package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;
import lombok.Setter;

@MappedSuperclass
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private double price;
    private String description;
    private String imageURL;
    private int discount;

    public Product() {}

    public Product(Long ID, double price, String description, String imageURL, int discount) {
        this.ID = ID;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        this.discount = discount;
    }

    //Getters
    public int getDiscount() { return this.discount; }

    public String getDescription() { return this.description; }

    public String getImageURL() { return this.imageURL; }

    public double getPrice() { return this.price; }

    public Long getID() { return this.ID; }

    abstract int getAmountLeft();

   //Setters
    abstract void setAmountLeft(int amountLeft);


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
