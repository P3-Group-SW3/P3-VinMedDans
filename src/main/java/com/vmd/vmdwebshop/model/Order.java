package com.vmd.vmdwebshop.model;

import java.util.*;

public class Order {
    private enum States {Modtaget, Pakket, Afsendt}

    private Long ID;
    private String address;
    private String mail;
    private String phoneNumber;
    private String customerName;
    private List<OrderLine> orderLines;
    private States orderState;

    public Order(){}

    public Order(Long ID, String address, String mail, String phoneNumber, String customerName, List<OrderLine> orderLines) {
        this.ID = ID;
        this.address = address;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.orderLines = orderLines;
        this.orderState = States.Modtaget;
    }

    private void setOrderState(String newState) {
        this.orderState = States.valueOf(newState);
    }
}
