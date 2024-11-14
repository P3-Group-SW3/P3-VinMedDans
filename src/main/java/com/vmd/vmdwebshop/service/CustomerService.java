package com.vmd.vmdwebshop.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import java.io.IOException;

@Service
public class CustomerService {


    public void setCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean cookieExists = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("customerData".equals(cookie.getName())) {
                    cookieExists = true;
                }
            }
        }

        if (!cookieExists) {
            String sessionID = request.getSession().getId();
            String legalAge = "false"; // default value

            String cookieValue = sessionID + "|" + legalAge;

            Cookie cookie = new Cookie("customerData", cookieValue);
            cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            response.addCookie(cookie);
        }
    }

    public void updateLegalAge(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("There are no cookies for this customer!");
            return;
        }

        String sessionID = request.getSession().getId();

        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                String customerID = value.split("\\|")[0];
                if (sessionID.equals(customerID)) {
                    if (value.contains("false")) {
                        String updatedValue = value.replace("false", "true");

                        cookie.setValue(updatedValue);
                        cookie.setMaxAge(7 * 24 * 60 * 60);
                        cookie.setSecure(true);
                        cookie.setHttpOnly(true);
                        cookie.setPath("/");

                        response.addCookie(cookie);

                        System.out.println("Cookie updated successfully!");
                        return;
                    } else {
                        System.out.println("Cookie is already set to true");
                        return;
                    }
                }
            }
        }

        System.out.println("No matching cookie found!");
    }

    public String getCustomerID(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("No cookies found!");
            return null;
        }

        String sessionID = request.getSession().getId();

        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                String customerID = value.split("\\|")[0];
                if (sessionID.equals(customerID)) {
                    return customerID;
                } else {
                    System.out.println("CustomerID is not the same as the sessionID.");
                    return null;
                }
            }
        }

        System.out.println("No matching cookie found!");
        return null;
    }

    public String getLegalAge(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("No cookies found!");
            return null;
        }

        String sessionID = request.getSession().getId();

        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                String customerID = value.split("\\|")[0];
                if (sessionID.equals(customerID)) {
                    return value.split("\\|")[1];
                } else {
                    System.out.println("SessionID does not match the customerID");
                    return null;
                }
            }
        }

        System.out.println("Matching cookie not found!");
        return null;
    }
}
