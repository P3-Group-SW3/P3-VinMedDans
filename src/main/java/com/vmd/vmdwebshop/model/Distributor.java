package com.vmd.vmdwebshop.model;

import com.vmd.vmdwebshop.repository.DistributorRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "distributor")
public class Distributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank(message = "This field must not be blank")
    @Size(max = 35)
    @Column(name = "name", nullable = false, length = 35)
    private String name;

    @NotBlank(message = "This field must not be blank")
    @Size(max = 80)
    @Column(name = "location", nullable = false, length = 80)
    private String location;

    
    @Column(name = "websiteURL", nullable = false, length = 200)
    private String websiteURL;

    // Empty Constructor
    public Distributor(){}

    // Constructor
    public Distributor(String name, String location, String websiteURL){
        this.name = name;
        this.location = location;
        this.websiteURL = websiteURL;
    }

    // Getters and Setters

    public Long getID(){
        return ID;
    }

    public void setID(Long ID){
        this.ID = ID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getWebsiteURL(){
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL){
        this.websiteURL = websiteURL;
    }

}
