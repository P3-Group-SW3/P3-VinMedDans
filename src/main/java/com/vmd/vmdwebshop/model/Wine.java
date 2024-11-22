package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;
import com.vmd.vmdwebshop.service.Product;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Wine")
public class Wine extends Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String name;


    private int amountLeft;

    //relationship med orderlines
    @OneToMany(mappedBy = "wine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderLine> orderLines = new HashSet<>();

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

    public void setName(String name){
        this.name = name;
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