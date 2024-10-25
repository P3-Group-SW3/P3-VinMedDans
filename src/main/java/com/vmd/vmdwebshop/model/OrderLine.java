package com.vmd.vmdwebshop.model;


import jakarta.persistence.*;


@Entity
@Table(name ="orderline")
public class OrderLine {

    @Id //creates a column that represents an ID for each row in the OrderLine table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int amount;

    /* @ManyToOne indicates that more than one instance (row) in the OrderLine table can be associated
    with only one instance (row) in the Product table. Because more than one customer can order the same product,
    therefore there will be multiple OrderLines that are associated with the same product.
    FetchType.LAZY means that the content is only fetched when being accessed.
    @JoinColumn takes the ID column from the Wine table and inserts it into the OrderLine table with the name Wine_id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Wine_id")

    private Wine wine;

    //Constructor
    public OrderLine(int ID, int amount) {
        this.ID = ID;
        this.amount = amount;
    }





}
