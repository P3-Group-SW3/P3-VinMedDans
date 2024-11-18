package com.vmd.vmdwebshop.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestCustomerService {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CustomerService customerService;

    // Test that verifies when there are no cookies that already exists in the incoming request,
    // then the setCustomerCookie method will correctly add a new "customerData" cookie to the response.
    @Test
    public void testSetCustomerCookie01() {
        when(request.getCookies()).thenReturn(null); // No existing cookies
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("testSessionID");

        customerService.setCustomerCookie(response, request); // Call to method

        // Verifying that the response has the right arguments and values.
        //argThat takes a predicate (typically a lambda expression) and applies it to the argument. It then will return true or false.
        verify(response).addCookie(argThat(cookie ->
                "customerData".equals(cookie.getName()) &&
                        cookie.getValue().equals("testSessionID|false") &&
                        cookie.getMaxAge() == 7 * 24 * 60 * 60 &&
                        cookie.getSecure() &&
                        cookie.isHttpOnly() &&
                        "/".equals(cookie.getPath())
        ));
    }

    // Test that verifies that no new cookie is added when a "customerData" cookie already exists.
    @Test
    public void testSetCustomerCookie02() {
        // Add a cookie object with the name and value that it is looking for.
        Cookie existingCookie = new Cookie("customerData", "existingSessionID|false");
        when(request.getCookies()).thenReturn(new Cookie[]{existingCookie}); // getCookies() returns an array with references to cookies.

        customerService.setCustomerCookie(response, request);

        verify(response, never()).addCookie(any(Cookie.class)); // any is used, because it does not matter what name the cookie has.
    }

    // Test that verifies that a new "customerData" cookie is added when JSESSIONID is present, but not a "customerData" cookie.
    @Test
    public void testSetCustomerCookie03() {
        // Cookie object for JSESSIONID, because it is the only cookie that will be present besides the "customerData" cookie.
        Cookie JSESSIONID = new Cookie("JSESSIONID", "anotherSessionID");
        when(request.getCookies()).thenReturn(new Cookie[]{JSESSIONID});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("anotherSessionID"); // returns same ID as the cookieValue for "customerData"

        customerService.setCustomerCookie(response, request);

        verify(response).addCookie(argThat(cookie ->
                "customerData".equals(cookie.getName()) &&
                        cookie.getValue().equals("anotherSessionID|false") &&
                        cookie.getMaxAge() == 7 * 24 * 60 * 60 &&
                        cookie.getSecure() &&
                        cookie.isHttpOnly() &&
                        "/".equals(cookie.getPath())
        ));
    }

    // Test that verifies if there is already a "customerData" that exists, then the cookie will be updated correctly.
    @Test
    public void testUpdateLegalAge01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false");
        when(request.getCookies()).thenReturn(new Cookie[]{customerCookie});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("testSessionID"); // returns same ID as cookieValue in "customerData".

        customerService.updateLegalAge(response, request);

        verify(response).addCookie(argThat(cookie ->
                "customerData".equals(cookie.getName()) &&
                        cookie.getValue().equals("testSessionID|true") && // "true" because now it should be updated.
                        cookie.getMaxAge() == 7 * 24 * 60 * 60 &&
                        cookie.getSecure() &&
                        cookie.isHttpOnly() &&
                        "/".equals(cookie.getPath())
        ));
    }

    // Test that verifies that no "customerData" cookie is updated, when the legalAge is already "true".
    @Test
    public void testUpdateLegalAge02() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|true"); // Already set to "true".
        when(request.getCookies()).thenReturn(new Cookie[]{customerCookie});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("testSessionID"); // Same ID as the customerID.

        customerService.updateLegalAge(response, request);

        verify(response, never()).addCookie(any(Cookie.class));
    }

    // Test that verifies that no "customerData" cookie is updated, when the customerID is not the same as the JSESSIONID.
    @Test
    public void testUpdateLegalAge03() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false");
        when(request.getCookies()).thenReturn(new Cookie[]{customerCookie});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("anotherSessionID"); // Different ID than the customerID.

        customerService.updateLegalAge(response, request);

        verify(response, never()).addCookie(any(Cookie.class));
    }

    // Test that verifies that no "customerData" cookie is added, when no cookies exist.
    @Test
    public void testUpdateLegalAge04() {
        when(request.getCookies()).thenReturn(null); // No cookies found.

        customerService.updateLegalAge(response, request);

        verify(response, never()).addCookie(any(Cookie.class));
    }

    // Test that verifies that no "customerData" cookie is added, when the JSESSIONID is present, but no "customerData" cookie exists.
    @Test
    public void testUpdateLegalAge05() {
        Cookie JSESSIONID = new Cookie("JSESSIONID", "anotherSessionID"); // JSESSIONID object.
        when(request.getCookies()).thenReturn(new Cookie[]{JSESSIONID});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("anotherSessionID");

        customerService.updateLegalAge(response, request);

        verify(response, never()).addCookie(any(Cookie.class));
    }

    // Test that verifies that the correct sessionID gets returned when a "customerData" cookie already exist.
    @Test
    public void testGetCustomerID01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false");
        when(request.getCookies()).thenReturn(new Cookie[]{customerCookie});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("testSessionID");

        String result = customerService.getCustomerID(request);

        assertEquals("testSessionID", result); // Tests if the result matches the expected result("testSessionID").
    }

    // Test that verifies that no sessionID gets returned when the JSESSIONID and customerID don't match.
    @Test
    public void testGetCustomerID02() {
        Cookie customerCookie = new Cookie("customerData", "differentSessionID|false");
        when(request.getCookies()).thenReturn(new Cookie[]{customerCookie});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("testSessionID"); // Different ID than the customerID.

        String result = customerService.getCustomerID(request);

        assertNull(result); // Tests if the result returns Null.
    }

    // Test that verifies than no sessionID gets returned when only the JSESSIONID is present, but not a "customerData" cookie.
    @Test
    public void testGetCustomerID03() {
        Cookie JSESSIONID = new Cookie("JSESSIONID", "anotherSessionID"); // JSESSIONID object.
        when(request.getCookies()).thenReturn(new Cookie[]{JSESSIONID});
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("anotherSessionID");

        String result = customerService.getCustomerID(request);

        assertNull(result);
    }

    // Test that verifies that no sessionID gets returned when no cookies already exist.
    @Test
    public void testGetCustomerID04() {
        when(request.getCookies()).thenReturn(null);

        String result = customerService.getCustomerID(request);

        assertNull(result);
    }

    // Test that verifies that if the 'customerData' cookie already exist, then return true.
    @Test
    public void testIfCookieExist01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false");
        when(request.getCookies()).thenReturn(new Cookie[]{customerCookie});

        boolean result = customerService.ifCookieExist(request);

        assertTrue(result);
    }

    // Test that verifies that if JSESSIONID is present but there is no 'customerData' cookie, then return false.
    @Test
    public void testIfCookieExist02() {
        Cookie JSESSIONID = new Cookie("JSESSIONID", "anotherSessionID");
        when(request.getCookies()).thenReturn(new Cookie[]{JSESSIONID});

        boolean result = customerService.ifCookieExist(request);

        assertFalse(result);
    }

    // Test that verifies if there are no cookies at all, then return false.
    @Test
    public void testIfCookieExist03() {
        when(request.getCookies()).thenReturn(null);

        boolean result = customerService.ifCookieExist(request);

        assertFalse(result);
    }

    // Test that verifies if the request is null, then throw IllegalArgumentException.
    @Test
    public void testIfCookieExist04() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.ifCookieExist(null);
        });

        assertEquals("HttpServletRequest cannot be null!", exception.getMessage());
    }

    // Test that verifies that a NullPointerException will get caught.
    @Test
    public void testIfCookieExist05() {
        when(request.getCookies()).thenThrow(new NullPointerException());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            customerService.ifCookieExist(request);
        });

        assertEquals("Error processing cookies: request may be invalid!", exception.getMessage());
    }

    // Test that verifies that a RuntimeException will get caught.
    @Test
    public void testIfCookieExist06() {
        when(request.getCookies()).thenThrow(new RuntimeException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.ifCookieExist(request);
        });

        assertEquals("An unexpected error occurred while checking cookies", exception.getMessage());
    }
}

