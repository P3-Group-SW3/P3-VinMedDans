package com.vmd.vmdwebshop.DTO;
import com.vmd.vmdwebshop.model.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderDto {

    @NotBlank(message = "Dette felt må ikke være blankt")
    @Size(max = 35, message = "Der må ikke være mere ind 35 karaktere")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Kun bogstaver, mellemrum, punktum, apostrof og bindestreg er tilladt")
    private String firstName;

    @NotBlank(message = "Dette felt må ikke være blankt")
    @Size(max = 35, message = "Der må ikke være mere ind 35 karaktere")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Kun bogstaver, mellemrum, punktum, apostrof og bindestreg er tilladt")
    private String lastName;

    @NotBlank(message = "Dette felt må ikke være blankt")
    @Email(message = "Email er krævet")
    private String email;

    @Pattern(regexp = "^(\\d{8}|\\+\\d{1,3}\\d{8,15})$", message = "Tillefon nummeret skal inten være 8 tal eller starte med land code.")
    private String phone;

    @NotBlank(message = "Dette felt må ikke være blankt")
    @Pattern(regexp = "^[a-zA-ZÆØÅæøå0-9,. ]*$", message = "Kun bogstaver, tal, komma, punktum og mellemrum er tilladt")
    private String address;

    @NotBlank(message = "Dette felt må ikke være blankt")
    @Size(min = 4, message = "Det skal være fire tegn")
    @Size(max = 4, message = "Det skal være fire tegn")
    @Pattern(regexp = "^\\d{4}$", message = "Postnummeret må kun indeholde fire tal")
    private String zipCode;

    @NotBlank(message = "Dette felt må ikke være blankt")
    @Size(max = 20, message = "Et by navn kan ikke være længere end 20 tegn.")
    @Pattern(regexp = "^[a-zA-ZÆØÅæøå ]*$", message = "Kun bogstaver og mellemrum er tilladt")
    private String city;

    /**
     * Creates an Order with it's information
     * @return Orders
     */
    public Orders createOrderFromInfo() {
        Orders order = new Orders(firstName, lastName, email, phone, address, zipCode, city, null);
        return order;
    }

    public OrderDto(){

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

    public void setPhone(String phone){
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
