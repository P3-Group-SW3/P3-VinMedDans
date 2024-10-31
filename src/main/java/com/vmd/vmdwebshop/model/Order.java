package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;

@Entity
@Table (name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String fullName;
    private String mail;
    private String phoneNumber;
    private String street;
    private String streetNum;
    private String zipCode;
    private String city;
}


