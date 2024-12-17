package com.vmd.vmdwebshop.controller;

import com.vmd.vmdwebshop.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * This GET request takes the request and the response objects as parameters and returns nothing.
     * @param response  // HttpServletResponse helps to send data from the servlet to the web browser.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     */
    @GetMapping("/createCookie")
    public void createCustomerCookie(HttpServletRequest request, HttpServletResponse response) {
        try {
            customerService.setCustomerCookie(response, request);
            System.out.println("Cookie has been set!");
        } catch (RuntimeException e) {
            System.err.println("An error occurred while trying to update the cookie: " + e.getMessage());
        }
    }

    /**
     * This GET request takes the request and response objects as parameters and returns nothing
     * @param response  // HttpServletResponse helps to send data from the servlet to the web browser.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     */
    @GetMapping("/updateCookie")
    public void updateCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        try {
            customerService.updateLegalAge(response, request);
        } catch (RuntimeException e) {
            System.err.println("An error occurred while trying to update the cookie: " + e.getMessage());
        }
    }

    /**
     * This GET request takes the request object as a parameter
     * and returns a map with a key-value pair of cookieAge and its value,
     * that is wrapped in a ResponseEntity.
     * @param request // HttpServletRequest helps to send data from the web browser to the servlet.
     * @return ResponseEntity<Map<String, String>>
     */
    @GetMapping("/cookieAge")
    public ResponseEntity<Map<String, String>> cookieAge(HttpServletRequest request) {
        try {
            String cookieAge = customerService.getCookieAge(request);
            if (cookieAge == null) {
                return ResponseEntity.noContent().build(); // HTTP 204: No Content
            }

            //Store cookieAge in a response object to avoid passing plain text
            Map<String, String> response = new HashMap<>();
            response.put("cookieAge", cookieAge);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}