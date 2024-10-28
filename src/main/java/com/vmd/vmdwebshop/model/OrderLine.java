package com.vmd.vmdwebshop.model;


import jakarta.persistence.*;


@Entity
@Table(name ="orderline")
public class OrderLine {

    @Id //creates a column that represents an ID for each row in the OrderLine table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private int amount;
    private double price;
    private Long wine_ID;

//    /* @ManyToOne indicates that more than one instance (row) in the OrderLine table can be associated
//    with only one instance (row) in the Product table. Because more than one customer can order the same product,
//    therefore there will be multiple OrderLines that are associated with the same product.
//    FetchType.LAZY means that the content is only fetched when being accessed.
//    @JoinColumn takes the ID column from the Wine table and inserts it into the OrderLine table with the name Wine_id */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Wine_id")
//    private Wine wine;



    //Constructor
    public OrderLine(Long ID, int amount, Wine wine) {
        this.ID = ID;
        this.amount = amount;
        this.price = wine.getPrice();
        this.wine_ID = wine.getID();
    }

    public double calculateOrderLine(){
        return amount * price;
    }





}
