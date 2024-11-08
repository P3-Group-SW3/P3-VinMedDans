package com.vmd.vmdwebshop.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import java.io.IOException;

@Service
public class CustomerService {

    // HUSK lav et tjek for om der allerede eksisterer en cookie med dette navn, for ellers vil den overskrive den opdaterede cookie til den gamle cookie
    public void setCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        String legalAge = "false"; // default value
        String sessionID = request.getSession().getId();


        String cookieValue = sessionID + "|" + legalAge;

        Cookie cookie = new Cookie("customerData", cookieValue);
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public String updateLegalAge(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "No cookies found.";
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

                        return "Cookie updated successfully!";
                    } else {
                        return "Cookie already set to true";
                    }
                }
            }
        }

        return "Matching cookie not found.";
    }

    public String getCustomerID(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "no cookies found!";
        }

        String sessionID = request.getSession().getId();

        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                String customerID = value.split("\\|")[0];
                if (sessionID.equals(customerID)) {
                    return customerID;
                } else {
                    return "SessionID does not match the customerID!";
                }
            }
        }

        return "No matching cookie found!";
    }

    public String getLegalAge(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "Cookies not found!";
        }

        String sessionID = request.getSession().getId();

        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                String customerID = value.split("\\|")[0];
                if (sessionID.equals(customerID)) {
                    return value.split("\\|")[1];
                } else {
                    return "SessionID does not match the customerID!";
                }
            }
        }

        return "Matching cookie not found!";
    }

    public boolean existingCookieCheck(@CookieValue(value = "customerData", required = false) String customerData) {
        return customerData != null;
    }
}
