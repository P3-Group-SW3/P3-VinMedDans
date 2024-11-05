package com.vmd.vmdwebshop.service;
import com.vmd.vmdwebshop.model.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

public class Orderinfo {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String zipCode;
    private String city;

    public Orders createOrderFromInfo() {
        return new Orders(firstName, lastName, email, phone, address, zipCode, city);
    }
}
