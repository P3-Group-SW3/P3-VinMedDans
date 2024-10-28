package com.vmd.vmdwebshop.model;
import java.util.*;

public class Cart {
    private ArrayList<OrderLine> orderLines;
    private String customerID;

    public Cart() {}
    public Cart(ArrayList<OrderLine> orderLines, Customer customer) {
        this.orderLines = orderLines;
        //this.customerID = customer.getID();
    }

    public void setOrderLines(int index) {
        this.orderLines.remove(index);
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (OrderLine orderLine : orderLines) {
            //total += orderLine.calculateOrderLine();
        }
        return total;
    }
}
