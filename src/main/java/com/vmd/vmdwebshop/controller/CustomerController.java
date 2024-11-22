package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/createCookie")
    public void createCustomerCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            customerService.setCustomerCookie(response, request);
            System.out.println("Cookie has been set!");
        } catch (RuntimeException e) {
            System.err.println("An error occurred while trying to update the cookie: " + e.getMessage());
        }
    }

    @GetMapping("/updateCookie")
    public void updateCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        try {
            customerService.updateLegalAge(response, request);
        } catch (RuntimeException e) {
            System.err.println("An error occurred while trying to update the cookie: " + e.getMessage());
        }
    }

    @GetMapping("/cookieAge")
    public ResponseEntity<String> cookieAge(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(customerService.getCookieAge(request));
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }


    }
}
