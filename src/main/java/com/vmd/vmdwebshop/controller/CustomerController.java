package com.vmd.vmdwebshop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    @GetMapping("/")
    public String frontPage() {
        return "Wub wub";
    }

    @GetMapping("/register-customer")
    public String createCustomerCookie(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();

        Cookie cookie = new Cookie("customerId", sessionId);
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // global cookie accessible everywhere

        response.addCookie(cookie);

        return "Cookie set successfully";
    }

    @GetMapping("/get")
    public String readCookie(@CookieValue(value = "customerId", defaultValue = "Atta") String id) {
        return "HEY! my customer id is " + id;
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
        Cookie cookie = new Cookie("customerId", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "cookie named " + cookie.getName() + " is now deleted";
    }
}
