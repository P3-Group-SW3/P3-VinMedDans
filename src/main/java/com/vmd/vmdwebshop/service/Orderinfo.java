package com.vmd.vmdwebshop.service;
import com.vmd.vmdwebshop.model.*;

public class Orderinfo {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String zipCode;
    private String city;

    /**
     * Creates an Order with it's information
     * @return
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
