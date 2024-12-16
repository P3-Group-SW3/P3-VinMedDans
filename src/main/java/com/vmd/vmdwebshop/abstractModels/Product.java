package com.vmd.vmdwebshop.abstractModels;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Type;

//abstract class used, as it could be used for other types of products in the future
//uses Hibernate Validation annotations for input validation
//subclasses inherits the input validation
@MappedSuperclass
public abstract class Product {

    @Min(value = 1, message = "The price must be more than 1 DKK")
    private double price;

    @NotBlank(message = "The wine must have a description")
    @Size(min = 1, max = 300)
    @Pattern(regexp = "^[a-zA-ZÆØÅæøå0-9,.? ]*$", message = "Kun bogstaver, tal, komma, punktum og mellemrum er tilladt")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "The wine must have an image URL")
    @Size(min = 1, max = 50)
    private String imageURL;

    private boolean activeState = true;

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

    public boolean getActiveState(){ return this.activeState; }



    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    //reverses the boolean value
    public void changeActiveState(){ activeState = !activeState; }

}
