package com.vmd.vmdwebshop.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name ="orderline")
public class OrderLine {

    @Id //creates a column that represents an ID for each row in the OrderLine table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "customerID")
    @NotBlank(message = "this field must not be empty")
    private String customerID;

    @Digits(integer = 3, fraction = 0, message = "this field must only consist of digits")
    @Min(value = 0, message = "the amount must not be less than 0")
    private int amount;

    /* @ManyToOne indicates that more than one instance (row) in the OrderLine table can be associated
    with only one instance (row) in the Product table. Because more than one customer can order the same product,
    therefore there will be multiple OrderLines that are associated with the same product.
    FetchType.LAZY means that the content is only fetched when being accessed.
    @JoinColumn takes the ID column from the Wine table and inserts it into the OrderLine table with the name Wine_id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wineID", nullable = false, insertable = false, updatable = false)
    private Wine wine; //hvorfor det??

    @Column(name = "wineID", nullable = false)
    @NotNull (message = "Wine ID must not be null")
    private Long wineID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Orders orders;

    //Empty Constructor
    public OrderLine() {}

    //Constructor
    public OrderLine(int amount, Long wineID, String customerID) {
        this.amount = amount;
        this.wineID = wineID;
        this.customerID = customerID;
    }



    public Long getID() {
        return this.ID;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getCustomerID() {return this.customerID;}

    public void removeCustomerID() {this.customerID = null; }

    public Long getWineID() {return this.wineID;}

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCustomerID(String customerID) {this.customerID = customerID; }

    public void setOrders(Orders orders) {
        this.orders = orders;
        this.customerID = null;
    }

}
