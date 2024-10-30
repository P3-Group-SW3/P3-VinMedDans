package com.vmd.vmdwebshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.Id;


@Entity
@Table(name="Wine")
public class Wine extends Product{

    private String name;
    private int amountLeft;

    public Wine(){}

    public Wine(Long ID, double price, String description, String imageURL, int discount, String name, int amountLeft) {
        super(ID, price, description, imageURL, discount);
        this.name = name;
        this.amountLeft = amountLeft;
    }

    String getName() { return this.name; }

    public int getAmountLeft() { return this.amountLeft; }

    public boolean canBePurchased(int amountPurchased){
        return amountLeft - amountPurchased >= 0;
    }

    @Override
    void setAmountLeft(int amountLeft) { this.amountLeft = amountLeft; }

    //MÅske er denne unødvendig
    void addToAmountLeft(int amountPurchased){ this.amountLeft += amountPurchased; }
}
