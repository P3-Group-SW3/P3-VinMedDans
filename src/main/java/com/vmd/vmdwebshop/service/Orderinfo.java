package com.vmd.vmdwebshop.service;
import com.vmd.vmdwebshop.model.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class Orderinfo {

    @NotBlank(message = "Dette felt må ikke være blankt")
    private String firstName;
    @NotBlank(message = "Dette felt må ikke være blankt")
    private String lastName;
    @NotBlank(message = "Dette felt må ikke være blankt")
    @Email(message = "Email er krævet")
    private String email;
    @Pattern(regexp = "^(\\d{8}|\\+\\d{1,3}\\d{8,15})$", message = "Tillefon nummeret skal inten være 8 tal eller starte med land code.")
    private String phone;
    @NotBlank(message = "Dette felt må ikke være blankt")
    private String address;
    @NotBlank(message = "Dette felt må ikke være blankt")
    
    private String zipCode;
    @NotBlank(message = "Dette felt må ikke være blankt")
    private String city;

    /**
     * Creates an Order with it's information
     * @return Orders
     */
    public Orders createOrderFromInfo() {
        Orders order = new Orders(firstName, lastName, email, phone, address, zipCode, city);
        return order;
    }

    public Orderinfo(){

    }

    // Getters and setters
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }

    public void SetPhone(String phone){
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode(){
        return zipCode;
    }


    public void setCity(String city) {
        this.city = city;
    }

    public String getCity(){
        return city;
    }
}
