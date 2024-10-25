package com.vmd.vmdwebshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Setter;

@MappedSuperclass
public abstract class Product {
    @Id @Column(name="")
    private String ID;
    @Column(name="")
    private int price;
    @Column(name="")
    private String description;
    @Column(name="")
    private String imageURL;
    @Column(name="")
    private int discount;

    public Product() {}

    public Product(String ID, int price, String description, String imageURL, int discount) {
        this.ID = ID;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        this.discount = discount;
    }

    public int getDiscount() { return this.discount; }

    public String getDescription() { return this.description; }

    public String getImageURL() { return this.imageURL; }

    public int getPrice() { return this.price; }

    public String getID() { return this.ID; }

    abstract void setAmountLeft(int amountLeft);

    abstract int getAmountLeft();

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
