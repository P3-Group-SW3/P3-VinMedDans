package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;
import com.vmd.vmdwebshop.abstractModels.Product;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Wine")
public class Wine extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank(message = "The wine must have a name")
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-ZÆØÅæøå0-9\\- ]*$", message = "Kun bogstaver, tal, mellemrum og bindestreg er tilladt")
    private String name;

    @Digits(integer = 4, fraction = 0, message = "There can't be more than 9999 wines in stock")
    @Min(value = 0, message = "The stock amount must not be less than 0")
    private int amountLeft;

    //relationship med orderlines
    @OneToMany(mappedBy = "wine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderLine> orderLines = new HashSet<>();

    // Constructors
    public Wine(){}

    public Wine(String description, String imageURL, double price, int amountLeft, String name) {
        super(price, description, imageURL);
        this.name = name;
        this.amountLeft = amountLeft;
    }

    // Getters and setters
    public Long getID() { return this.ID; }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAmountLeft() {
        return amountLeft;
    }

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