package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;
import com.vmd.vmdwebshop.abstractModels.Product;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

//uses Hibernate validation
//inherits from the Product class, also input validation
//@Entity is for the database, to tell the database that it must be able to store objects of this class
//Table generates the table name
@Entity
@Table(name="Wine")
public class Wine extends Product {

    //these annotation are for the database
    //tells that this attribute should be the primary key, and the generation strategy
    //The database generates this field automatically
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank(message = "The wine must have a name")
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-ZÆØÅæøå0-9\\- ]*$", message = "Kun bogstaver, tal, mellemrum og bindestreg er tilladt")
    private String name;

    @Digits(integer = 4, fraction = 0, message = "There can't be more than 9999 wines in stock")
    @Min(value = 0, message = "The stock amount must not be less than 0")
    private int stock;

    @Max(value = 100, message = "The AlcoholPercentage can't be above a 100%")
    private double alcoholPercentage;

    @Size(max = 300)
    @Column(columnDefinition = "TEXT")
    @Pattern(regexp = "^[a-zA-ZÆØÅæøå0-9\\- ]*$", message = "Kun bogstaver, tal, mellemrum og bindestreg er tilladt")
    private String contents;

    @Size(max = 300)
    @Column(columnDefinition = "TEXT")
    @Pattern(regexp = "^[a-zA-ZÆØÅæøå0-9\\- ]*$", message = "Kun bogstaver, tal, mellemrum og bindestreg er tilladt")
    private String TasteDescription;

    //relationship med orderlines
    @OneToMany(mappedBy = "wine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderLine> orderLines = new HashSet<>();

    // Constructors
    public Wine(){}

    public Wine(String description, String imageURL, double price, int stock, String name) {
        super(price, description, imageURL);
        this.name = name;
        this.stock = stock;
    }

    // Getters and setters
    public Long getID() { return this.ID; }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    // Getter for amountLeft
    public int getStock() {
        return stock;
    }

    // Setter for amountLeft
    public void setStock(int amountLeft) {
        this.stock = amountLeft;
    }

    // Method to check if the wine can be purchased
    //is used in the creation of an order
    public boolean canBePurchased(int amountPurchased) {
        return stock - amountPurchased >= 0;
    }

    // Method to add to the amount left
    public void addToStock(int amountPurchased) {
        this.stock += amountPurchased;
    }


    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage( double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTasteDescription() {
        return TasteDescription;
    }

    public void setTasteDescription(String tasteDescription) {
        TasteDescription = tasteDescription;
    }
}