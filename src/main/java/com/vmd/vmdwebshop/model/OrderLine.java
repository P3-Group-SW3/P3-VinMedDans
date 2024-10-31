package com.vmd.vmdwebshop.model;


import jakarta.persistence.*;


@Entity
@Table(name ="orderline")
public class OrderLine {

    @Id //creates a column that represents an ID for each row in the OrderLine table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "customerID", nullable = false)
    private Long customerID;

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
    private Long wineID;

    //Empty Constructor
    public OrderLine() {}

    //Constructor
    public OrderLine(Long ID, int amount, Wine wine, Long customerID) {
        this.ID = ID;

        this.customerID = customerID;
        this.amount = amount;
        this.wineID = wine.getID();
    }

    public Long getID() {
        return this.ID;
    }

    public int getAmount() {
        return this.amount;
    }

    public Long getCustomerID() {return this.customerID;}

    public Long getWineID() {return this.wineID;}

    public void setAmount(int amount) {
        this.amount = amount;
    }



    //Method for increasing or decreasing the product type amount in the orderline
    public void editOrderLineAmount(boolean increment){
        if (increment) {
            if (wine.getAmountLeft() > 0) amount += 1;
        }
        else {
            if (amount > 0) amount -= 1;
        }
    }

}
