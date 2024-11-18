package com.vmd.vmdwebshop.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import java.io.IOException;

@Service
public class CustomerService {

    /**
     * setCustomerCookie checks if there is already a cookie called "customerData" and if there is not,
     * then it will set a cookie with the same name, that has both the JSESSIONID and legalAge as values, as well as
     * a maxAge of 7 days.
     * @param response  // HttpServletResponse helps to send data from the servlet to the web browser.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     */
    public void setCustomerCookie(HttpServletResponse response, HttpServletRequest request) {
        try {
            // If there are no existing "customerData" cookie, then create a new one.
            if (!ifCookieExist(request)) {
                String sessionID = request.getSession().getId(); // Gets the JSESSIONID.
                String legalAge = "false"; // default value
                String cookieAge = "new"; // Age of cookie. Needed for frontend.

                String cookieValue = sessionID + "|" + legalAge + "|" + cookieAge; // Concatenates sessionID, legalAge and cookieAge together with a separator "|".

                Cookie cookie = new Cookie("customerData", cookieValue); // Creates Cookie object.
                cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days.
                cookie.setSecure(true); // Cookie can only be sent over secure HTTPS connections.
                cookie.setHttpOnly(true); // Cookie cannot get accessed or modified via Javascript.
                cookie.setPath("/"); // Cookie is accessible to all pages in the domain.

                response.addCookie(cookie); // cookie gets added to the response.
            }
        } catch (NullPointerException exc) {
            System.err.println("A required object was null(request, response or session): " + exc.getMessage());
        } catch (IllegalArgumentException exc) {
            System.err.println("Name or value of cookie is invalid: " + exc.getMessage());
        } catch (IllegalStateException exc) {
            System.err.println("Failed to receive session from request: " + exc.getMessage());
        } catch (Exception exc) {
            System.err.println("An unexpected error occurred while setting the customer cookie: " + exc.getMessage());
        }
    }

    /**
     * updateLegalAge checks if both the JSESSIONID and the customerID in the "customerData" cookie are the same and
     * if the value of legalAge is "false", then replace it with "true" and then return.
     * @param response  // HttpServletResponse helps to send data from the servlet to the web browser.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     */
    public void updateLegalAge(HttpServletResponse response, HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent by the client with the request.

            if (cookies == null) {
                throw new IllegalStateException("There are no cookies for this customer!");
            }

            String sessionID = request.getSession().getId(); // Gets the JSESSIONID.

            // If there is a "customerData" cookie, then save its value in a variable.
            for (Cookie cookie : cookies) {                     // For-each loop.
                if ("customerData".equals(cookie.getName())) {
                    String value = cookie.getValue();

                    String customerID = value.split("\\|")[0]; // Saves the value before "|" in variable.

                    // If the sessionID and customerID match and the value contains "false", then change it to "true" and update cookie.
                    if (sessionID.equals(customerID)) {
                        if (value.contains("false")) {
                            String updatedValue = value.replace("false", "true");

                            // Has to be the exact same as when created, or else it will create a new cookie.
                            cookie.setValue(updatedValue); // Sets value of the cookie to the updatedValue.
                            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                            cookie.setSecure(true); // Cookie can only be sent over secure HTTPS connections.
                            cookie.setHttpOnly(true); // Cookie cannot get accessed or modified via Javascript.
                            cookie.setPath("/"); // Cookie is accessible to all pages in the domain.

                            response.addCookie(cookie); // cookie gets added to the response.

                            System.out.println("legalAge updated successfully!");

                            if (value.contains("new")) {
                                String updatedCookieAge = value.replace("new", "old");

                                // Has to be the exact same as when created, or else it will create a new cookie.
                                cookie.setValue(updatedCookieAge); // Sets value of the cookie to the updatedCookieAge.
                                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                                cookie.setSecure(true); // Cookie can only be sent over secure HTTPS connections.
                                cookie.setHttpOnly(true); // Cookie cannot get accessed or modified via Javascript.
                                cookie.setPath("/"); // Cookie is accessible to all pages in the domain.

                                response.addCookie(cookie); // cookie gets added to the response.

                                System.out.println("cookieAge updated successfully!");
                                return;
                            } else {
                                throw new IllegalStateException("Cookie is already set to old.");
                            }

                        } else {
                            throw new IllegalStateException("Cookie is already set to true.");
                        }
                    }
                }
            }

            throw new IllegalStateException("No matching 'customerData' cookie found!");

        } catch (NullPointerException exc) {
            throw new NullPointerException("A required object is null (request, response or session): " + exc.getMessage());
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException("Invalid format or value in 'customerData' cookie: " + exc.getMessage());
        } catch (IllegalStateException exc) {
            throw new IllegalStateException("Failed to update cookie: " + exc.getMessage());
        } catch (Exception exc) {
            throw new RuntimeException("An unexpected error occurred while updating the legal age: " + exc.getMessage());
        }
    }

    /**
     * getCustomerID checks if a "customerData" cookie exist and if it does, it returns the customerID value.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     * @return          // Will only return the customerID if a " customerData" cookie exist, otherwise null.
     */
    public String getCustomerID(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent by the client with the request.
            if (cookies == null) {
                throw new IllegalStateException("There are no cookies for this customer!");
            }

            String sessionID = request.getSession().getId(); // Gets the JSESSIONID.

            // If there is a "customerData" cookie, then save its value in a variable.
            for (Cookie cookie : cookies) {
                if ("customerData".equals(cookie.getName())) {
                    String value = cookie.getValue();

                    String customerID = value.split("\\|")[0]; // Saves the String before "|" in variable.

                    if (sessionID.equals(customerID)) {
                        return customerID;
                    } else {
                        throw new IllegalArgumentException("CustomerID is not the same as the sessionID!");
                    }
                }
            }

            throw new IllegalStateException("No matching 'customerData' cookie found!");

        } catch (NullPointerException exc) {
            throw new NullPointerException("A required object is null (request, response or session): " + exc.getMessage());
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException("Invalid format or value in 'customerData' cookie: " + exc.getMessage());
        } catch (Exception exc) {
            throw new RuntimeException("An unexpected error occurred while retrieving the customerID: " + exc.getMessage());
        }
    }

    /**
     * getLegalAge checks if a "customerData" cookie exist and if it does, then return the value of legalAge.
     * @param request   // HttpServletRequest helps to send data from the web browser to the servlet.
     * @return          // Will only return the legalAge if a " customerData" cookie exist, otherwise null.
     */
    public String getLegalAge(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent by the client with the request.
            if (cookies == null) {
                throw new IllegalStateException("There are no cookies for this customer!");
            }

            String sessionID = request.getSession().getId(); // Gets the JSESSIONID.

            // If there is a "customerData" cookie, then save its value in a variable.
            for (Cookie cookie : cookies) {
                if ("customerData".equals(cookie.getName())) {
                    String value = cookie.getValue();

                    String customerID = value.split("\\|")[0]; // Saves the String before "|" in variable.

                    if (sessionID.equals(customerID)) {
                        return value.split("\\|")[1]; // Returns the String after "|", which is the legalAge value.
                    } else {
                        throw new IllegalArgumentException("CustomerID is not the same as the sessionID!");
                    }
                }
            }

            throw new IllegalStateException("No matching 'customerData' cookie found!");

        } catch (NullPointerException exc) {
            throw new NullPointerException("A required object is null (request, response or session): " + exc.getMessage());
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException("Invalid format or value in 'customerData' cookie: " + exc.getMessage());
        } catch (Exception exc) {
            throw new RuntimeException("An unexpected error occurred while retrieving the customerID: " + exc.getMessage());
        }
    }

    public boolean ifCookieExist(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest cannot be null!");
        }

        try {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("customerData".equals(cookie.getName())) {
                        return true;
                    }
                }
            }

            return false;

        } catch (NullPointerException exc) {
            throw new IllegalStateException("Error processing cookies: request may be invalid!");
        } catch (Exception exc) {
            System.err.println("Unexpected error: " + exc.getMessage());
            throw new RuntimeException("An unexpected error occurred while checking cookies", exc);
        }
    }

    public String getCookieAge(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies(); // Retrieves an array of Cookie objects sent by the client with the request.
            if (cookies == null) {
                throw new IllegalStateException("There are no cookies for this customer!");
            }

            String sessionID = request.getSession().getId(); // Gets the JSESSIONID.

            // If there is a "customerData" cookie, then save its value in a variable.
            for (Cookie cookie : cookies) {
                if ("customerData".equals(cookie.getName())) {
                    String value = cookie.getValue();

                    String customerID = value.split("\\|")[0]; // Saves the String before "|" in variable.

                    if (sessionID.equals(customerID)) {
                        return value.split("\\|")[2]; // Returns the String after the second "|", which is the cookieAge value.
                    } else {
                        throw new IllegalArgumentException("CustomerID is not the same as the sessionID!");
                    }
                }
            }

            throw new IllegalStateException("No matching 'customerData' cookie found!");

        } catch (NullPointerException exc) {
            throw new NullPointerException("A required object is null (request, response or session): " + exc.getMessage());
        } catch (IllegalArgumentException exc) {
            throw new IllegalArgumentException("Invalid format or value in 'customerData' cookie: " + exc.getMessage());
        } catch (Exception exc) {
            throw new RuntimeException("An unexpected error occurred while retrieving the customerID: " + exc.getMessage());
        }
    }
}
