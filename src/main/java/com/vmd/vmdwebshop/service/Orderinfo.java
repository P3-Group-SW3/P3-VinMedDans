package com.vmd.vmdwebshop.service;
import com.vmd.vmdwebshop.model.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

public class Orderinfo {
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String phone;
    @Getter @Setter
    private String address;
    @Getter @Setter
    private String zipCode;
    @Getter @Setter
    private String city;

    public Orders createOrderFromInfo() {
        Orders order = new Orders(firstName, lastName, email, phone, address, zipCode, city);
        return order;
    }

    public Orderinfo(){

    }


}
