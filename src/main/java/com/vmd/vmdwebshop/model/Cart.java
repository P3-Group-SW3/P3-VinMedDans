package com.vmd.vmdwebshop.model;
import java.util.*;

public class Cart {
    private ArrayList<OrderLine> items;
    private Long customerID;

    public Cart() {}
    public Cart(ArrayList<OrderLine> items, Customer customer) {
        this.items = items;
        //this.customerID = customer.getID();
    }

    public void removeOrderLine(int index) {
        this.items.remove(index);
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (OrderLine orderLine : items) {
            total += orderLine.calculateOrderLine();
        }
        return total;
    }
}
