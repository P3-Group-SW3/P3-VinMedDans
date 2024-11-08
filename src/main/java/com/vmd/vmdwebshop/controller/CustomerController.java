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


    @GetMapping("/getallcookies")
    public String readAllCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
        }
        return "No cookies found";
    }

    @GetMapping("/deletecookies")
    public String deleteCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie("customerData", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "cookie named " + cookie.getName() + " is now deleted";
    }
}
