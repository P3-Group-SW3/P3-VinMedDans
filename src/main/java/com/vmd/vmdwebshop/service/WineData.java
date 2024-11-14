package com.vmd.vmdwebshop.service;

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

    public double getPrice() { return this.price; }

    public String getDescription() { return this.description; }

    public int getAmountLeft() { return this.amountLeft; }

    public String getImageURL() { return this.imageURL; }

    public String getName() { return this.name; }
}
