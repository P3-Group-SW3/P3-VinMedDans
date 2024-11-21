// src/main/java/com/vmd/vmdwebshop/model/Order.java
package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders") // Renamed to avoid SQL reserved keyword conflict
public class Orders {



    /**
     * Denne enum er til fortælle hvilken state pakken er for levering
     *
     */
    public enum State {
        REGISTERED,
        CONFIRMED,
        PACKED,
        SHIPPED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String fullName;
    private String mail;
    private String phoneNumber;
    private String adress;
    private String zipCode;
    private String city;
    private State state;
    private Date date;

    //relationship med orderlines
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderLine> orderLines = new HashSet<>();


    //constructors
    public Orders() {
    }

    public Orders(String firstName, String lastName, String mail, String phoneNumber, String adress, String zipCode, String city) {
        this.fullName = firstName + " " + lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.zipCode = zipCode;
        this.city = city;
    }

    // tilføjer orderline
    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    public Set<OrderLine> getOrderLines(){
        return orderLines;
    }


    // Getters and setters
    public Long getID() {
        return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() { return state; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //Slet
    //public void setID(Long id){this.ID = id;}
}

