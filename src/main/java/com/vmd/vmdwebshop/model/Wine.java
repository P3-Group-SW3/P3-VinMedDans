package com.vmd.vmdwebshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.vmd.vmdwebshop.service.Product;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="Wine")
public class Wine extends Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String name;


    private int amountLeft;

    public Wine(){}

    public Wine(String description, String imageURL,  double price, int amountLeft, String name) {
        super(price, description, imageURL);
        this.name = name;
        this.amountLeft = amountLeft;
    }

    public Long getID() { return this.ID; }


    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for amountLeft
    public int getAmountLeft() {
        return amountLeft;
    }

    // Setter for amountLeft
    public void setAmountLeft(int amountLeft) {
        this.amountLeft = amountLeft;
    }

    // Method to check if the wine can be purchased
    public boolean canBePurchased(int amountPurchased) {
        return amountLeft - amountPurchased >= 0;
    }

    // Method to add to the amount left
    public void addToAmountLeft(int amountPurchased) {
        this.amountLeft += amountPurchased;
    }
}