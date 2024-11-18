package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CustomerController {
    // SKAL FIKSE ROUTES NÅR FRONT END OG BACK END BLIVER KOPLET SAMMEN.
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public void redirectToCreateCustomerCookie(HttpServletRequest request, HttpServletResponse response) {
        createCustomerCookie(request, response);
    }

    @GetMapping("/api/createcookie")
    public void createCustomerCookie(HttpServletRequest request, HttpServletResponse response) {
        customerService.setCustomerCookie(response, request);

        System.out.println("Cookie has been set!");
    }

    @GetMapping("/api/updatecookie")
    public void updateCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        customerService.updateLegalAge(response, request);
    }

    @GetMapping("/api/customerID")
    public String customerID(HttpServletRequest request) {
        return customerService.getCustomerID(request);
    }

    @GetMapping("/api/legalAge")
    public String legalAge(HttpServletRequest request) {
        return customerService.getLegalAge(request);
    }
}
