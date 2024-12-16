package com.vmd.vmdwebshop.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    /**
     * setCustomerCookie checks if there is already a cookie called "customerData" and if there is not,
     * then it will set a cookie with the same name, that has both the JSESSIONID, legalAge and cookieAge as values,
     * as well as a maxAge of 7 days.
     * @param response  // HttpServletResponse helps to send data from the servlet to the web browser.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     */
    public void setCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        // If the request is null, throw new illegalArgumentException.
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null!");
        }

        try {
            // If there are no existing "customerData" cookie, then create a new one.
            if (!ifCookieExist(request)) {
                String sessionID = request.getSession().getId(); // Gets the JSESSIONID.
                String legalAge = "false"; // default value
                String cookieAge = "new"; // Age of cookie. Needed for frontend.

                String cookieValue = sessionID + "|" + legalAge + "|" + cookieAge; /* Concatenates sessionID,
                                                                                      legalAge and cookieAge together
                                                                                      with a separator "|".          */

                Cookie cookie = new Cookie("customerData", cookieValue); // Creates Cookie object.
                cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days.
                cookie.setSecure(true); // Cookie can only be sent over secure HTTPS connections.
                cookie.setHttpOnly(true); // Cookie cannot get accessed or modified via Javascript.
                cookie.setPath("/"); // Cookie is accessible to all pages in the domain.

                response.addCookie(cookie); // cookie gets added to the response.
            }
        } catch (RuntimeException e) {
            System.err.println("An unexpected error occurred while setting the customer cookie: " + e.getMessage());
        }
    }

    /**
     * updateLegalAge checks if both the JSESSIONID and the customerID in the "customerData" cookie are the same and
     * if the value of legalAge is "false" and cookieAge is "new", then replace legalAge with "true" and
     * cookieAge with "old". Then return.
     * @param response  // HttpServletResponse helps to send data from the servlet to the web browser.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     */
    public void updateLegalAge(HttpServletResponse response, HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null!");
        }

        // If the 'customerData' cookie does not exist, throw new illegalStateException.
        if (!ifCookieExist(request)) {
            throw new IllegalStateException("There are no 'customerData' cookie for this customer!");
        }

        // We do this again, because we need the value from the cookie (this is also being done in ifCookieExist).
        Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent with the request.

        // If there is a "customerData" cookie, then save its value in a variable.
        for (Cookie cookie : cookies) {                     // For-each loop.
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                // If the value contains both "false" and "new", then change them to "true" and "old" and update cookie
                if (value.contains("false") && value.contains("new")) {
                    String updatedValue = value.replace("false", "true")
                                            .replace("new", "old");

                    // Has to be the exact same as when created, or else it will create a new cookie.
                    cookie.setValue(updatedValue); // Sets value of the cookie to the updatedValue.
                    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    cookie.setSecure(true); // Cookie can only be sent over secure HTTPS connections.
                    cookie.setHttpOnly(true); // Cookie cannot get accessed or modified via Javascript.
                    cookie.setPath("/"); // Cookie is accessible to all pages in the domain.

                    response.addCookie(cookie); // cookie gets added to the response.

                    System.out.println("Cookie updated successfully!");
                    return;
                } else {
                    throw new IllegalStateException("Cookie is already set to true.");
                }
            }
        }

        throw new IllegalStateException("No matching 'customerData' cookie found!");
    }

    /**
     * getCustomerID checks if a "customerData" cookie exist and if it does, it returns the customerID value.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     * @return          // Will only return the value of customerID (String) if a " customerData" cookie exist,
     *                  // otherwise throw new exception.
     */
    public String getCustomerID(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null!");
        }

        Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent with the request.
        if (cookies == null) {
            throw new IllegalStateException("There are no cookies for this customer!");
        }

        // If there is a "customerData" cookie, then save its value in a variable and return the customerID.
        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                return value.split("\\|")[0]; // Returning the value before the first "|", the customerID.
            }
        }

        throw new IllegalStateException("No matching 'customerData' cookie found!");
    }

    /**
     * getLegalAge checks if a "customerData" cookie exist and if it does, then return the value of legalAge.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     * @return          // Will only return the value of legalAge (String) if a " customerData" cookie exist,
     *                  // otherwise throw new exception.
     */
    public String getLegalAge(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null!");
        }

        Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent with the request.
        if (cookies == null) {
            throw new IllegalStateException("There are no cookies for this customer!");
        }

        // If there is a "customerData" cookie, then save its value in a variable and return the legalAge.
        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                return value.split("\\|")[1]; // Returns the String between the two "|", the legalAge value.
            }
        }

        throw new IllegalStateException("No matching 'customerData' cookie found!");
    }

    /**
     * ifCookieExist checks if the 'customerData' cookie exist and if it does, return true, else return false.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     * @return          // Returns true if 'customerData' cookie exist, otherwise return false.
     */
    public boolean ifCookieExist(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null!");
        }

        Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent with the request.

        // If there is a "customerData" cookie, then return true.
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("customerData".equals(cookie.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * getCookieAge checks if a "customerData" cookie exist and if it does, then return the value of cookieAge.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     * @return          // Will only return the value of cookieAge (String) if a " customerData" cookie exist,
     *                  // otherwise throw new exception.
     */
    public String getCookieAge(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null!");
        }

        Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent with the request.
        if (cookies == null) {
            throw new IllegalStateException("There are no cookies for this customer!");
        }

        // If there is a "customerData" cookie, then save its value in a variable and return the cookieAge.
        for (Cookie cookie : cookies) {
            if ("customerData".equals(cookie.getName())) {
                String value = cookie.getValue();

                return value.split("\\|")[2]; // Returns the String after the second "|", the cookieAge value.
            }
        }

        throw new IllegalStateException("No matching 'customerData' cookie found!");
    }
}
