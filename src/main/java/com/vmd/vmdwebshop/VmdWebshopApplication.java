package com.vmd.vmdwebshop;

import com.vmd.vmdwebshop.model.Wine;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vmd.vmdwebshop.service.*;
import com.vmd.vmdwebshop.controller.*;

@SpringBootApplication
public class VmdWebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(VmdWebshopApplication.class, args);
    }
}
