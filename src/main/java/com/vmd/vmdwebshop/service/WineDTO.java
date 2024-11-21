package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Wine;
import jakarta.validation.constraints.*;

public class WineDTO {
    private int ID;

    @NotBlank(message = "The wine must have a description")
    @Size(min = 1, max = 250)
    private String description;

    @NotBlank(message = "The wine must have an image URL")
    @Size(min = 1, max = 20)
    private String imageURL;

    @Digits(integer = 5, fraction = 0, message = "The price can't be higher than 9999")
    @Min(value = 1, message = "The price must be more than 1 DKK")
    private double price;

    @Digits(integer = 4, fraction = 0, message = "There can't be more than 9999 wines in stock")
    @Min(value = 0, message = "The stock amount must not be less than 0")
    private int amountLeft;

    @NotBlank(message = "The wine must have a name")
    @Size(min = 1, max = 20)
    private String name;

    public WineDTO(int ID, String description, String imageURL, double price, int amountLeft, String name){
        this.ID = ID;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
        this.amountLeft = amountLeft;
        this.name = name;
    }


    public int getID(){ return this.ID; }

    public double getPrice() { return this.price; }

    public String getDescription() { return this.description; }

    public int getAmountLeft() { return this.amountLeft; }

    public String getImageURL() { return this.imageURL; }

    public String getName() { return this.name; }


    public Wine createWineFromWineData(){
        return new Wine(description, imageURL, price, amountLeft, name);
    }
}
