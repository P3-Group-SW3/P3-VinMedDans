package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.service.CustomerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String redirectToCreateCustomerCookie(HttpServletRequest request, HttpServletResponse response) {
        return createCustomerCookie(request, response);
    }

    @GetMapping("/api/createcookie")
    public String createCustomerCookie(HttpServletRequest request, HttpServletResponse response) {
        customerService.setCustomerCookie(response, request);

        return "Customer cookie has been set!";
    }

    @GetMapping("/api/updatecookie")
    public String updateCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        return customerService.updateLegalAge(response, request);
    }


}
