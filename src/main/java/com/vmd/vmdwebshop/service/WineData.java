package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Wine;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class WineData {
    private int ID;
    private String description;
    private String imageURL;
    private double price;
    private int amountLeft;
    private String name;

    public WineData(int ID, String description, String imageURL, double price, int amountLeft, String name){
        this.ID = ID;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
        this.amountLeft = amountLeft;
        this.name = name;
    }


    public int getID(){ return this.ID; }

    @Digits(integer = 5, fraction = 0, message = "The price can't be higher than 9999")
    @Min(value = 1, message = "The price must be more than 1 DKK")
    public double getPrice() { return this.price; }

    @NotBlank(message = "The wine must have a description")
    public String getDescription() { return this.description; }

    @Digits(integer = 4, fraction = 0, message = "There can't be more than 9999 wines in stock")
    @Min(value = 0, message = "The stock amount must not be less than 0")
    public int getAmountLeft() { return this.amountLeft; }

    @NotBlank(message = "The wine must have an image URL")
    public String getImageURL() { return this.imageURL; }

    @NotBlank(message = "The wine must have a name")
    public String getName() { return this.name; }

    public Wine createWineFromWineData(){
        return new Wine(description, imageURL, price, amountLeft, name);
    }
}
