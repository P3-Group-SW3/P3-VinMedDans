package com.vmd.vmdwebshop.service;

import jakarta.servlet.http.*;
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

    // Testing on setCustomerCookie method

    // Test that verifies when there are no cookies that already exists in the incoming request,
    // then the setCustomerCookie method will correctly add a new "customerData" cookie to the response.
    @Test
    public void testSetCustomerCookie01() {
        CustomerService spyService = spy(customerService);
        doReturn(false)
                .when(spyService)
                .ifCookieExist(request);

        when(request.getSession())
                .thenReturn(session);
        when(session.getId())
                .thenReturn("testSessionID");

        spyService.setCustomerCookie(response, request);

        // Verifying that the response has the right arguments and values.
        //argThat takes a predicate (typically a lambda expression) and applies it to the argument. It then will return true or false.
        verify(response).addCookie(argThat(cookie ->
                "customerData".equals(cookie.getName()) &&
                        cookie.getValue().equals("testSessionID|false|new") &&
                        cookie.getMaxAge() == 7 * 24 * 60 * 60 &&
                        cookie.getSecure() &&
                        cookie.isHttpOnly() &&
                        "/".equals(cookie.getPath())
        ));
    }

    // Test that verifies that when ifCookieExist return true, then no cookie will be added to the response.
    @Test
    public void testSetCustomerCookie02() {
        CustomerService spyService = spy(customerService);
        doReturn(true)
                .when(spyService)
                .ifCookieExist(request);

        spyService.setCustomerCookie(response, request);

        verify(response, never()).addCookie(any(Cookie.class)); // any is used, because it does not matter what name the cookie has.
    }

    // Test that verifies that if there is thrown an IllegalArgumentException from ifCookieExist, then it will be caught in setCustomerCookie.
    @Test
    public void testSetCustomerCookie03() {
        CustomerService spyService = spy(customerService);
        doThrow(new IllegalArgumentException("Mocked IllegalArgumentException"))
                .when(spyService)
                .ifCookieExist(request);

        assertDoesNotThrow(() -> spyService.setCustomerCookie(response, request));

        verify(response, never()).addCookie(any(Cookie.class));

        verifyNoMoreInteractions(response);
    }

    // Test that verifies if the request is null, then throw IllegalArgumentException.
    @Test
    public void testSetCustomerCookie04() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.setCustomerCookie(response,null);
        });

        assertEquals("HttpServletRequest cannot be null!", exception.getMessage());
    }

    // Testing on updateLegalAge method

    // Test that verifies if there is already a "customerData" that exists, then the cookie will be updated correctly.
    @Test
    public void testUpdateLegalAge01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false|new");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        customerService.updateLegalAge(response, request);

        verify(response).addCookie(argThat(cookie ->
                "customerData".equals(cookie.getName()) &&
                        cookie.getValue().equals("testSessionID|true|old") && // "true" because now it should be updated.
                        cookie.getMaxAge() == 7 * 24 * 60 * 60 &&
                        cookie.getSecure() &&
                        cookie.isHttpOnly() &&
                        "/".equals(cookie.getPath())
        ));
    }

    // Test that verifies that an illegalStateException gets thrown, when the legalAge and cookieAge are already updated.
    @Test
    public void testUpdateLegalAge02() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|true|old");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            customerService.updateLegalAge(response, request);
        });

        assertEquals("Cookie is already set to true.", exception.getMessage());
    }

    // Test that verifies that an illegalStateException is thrown, when the legalAge is already set to "true".
    @Test
    public void testUpdateLegalAge03() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|true|new");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            customerService.updateLegalAge(response, request);
        });

        assertEquals("Cookie is already set to true.", exception.getMessage());
    }

    // Test that verifies that an illegalStateException is thrown, when the cookieAge is already set to "old".
    @Test
    public void testUpdateLegalAge04() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false|old");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            customerService.updateLegalAge(response, request);
        });

        assertEquals("Cookie is already set to true.", exception.getMessage());
    }

    // Test that verifies that an illegalStateException gets thrown, when the cookie name does not equal 'customerData'.
    @Test
    public void testUpdateLegalAge05() {
        Cookie JSESSIONID = new Cookie("JSESSIONID", "anotherSessionID");
        when(request.getCookies())
                .thenReturn(new Cookie[]{JSESSIONID});

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            customerService.updateLegalAge(response, request);
        });

        assertEquals("There are no 'customerData' cookie for this customer!", exception.getMessage());
    }

    // Test that verifies if the request is null, then throw IllegalArgumentException.
    @Test
    public void testUpdateLegalAge06() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateLegalAge(response, null);
        });

        assertEquals("HttpServletRequest cannot be null!", exception.getMessage());
    }

    // Testing on getCustomerID method

    // Test that verifies that the correct sessionID gets returned when a "customerData" cookie already exist.
    @Test
    public void testGetCustomerID01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false|new");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        String result = customerService.getCustomerID(request);

        assertEquals("testSessionID", result); // Tests if the result matches the expected result("testSessionID").
    }

    // Test that verifies than an illegalStateException gets thrown, when only the JSESSIONID is present, but not a "customerData" cookie.
    @Test
    public void testGetCustomerID02() {
        Cookie JSESSIONID = new Cookie("JSESSIONID", "anotherSessionID"); // JSESSIONID object.
        when(request.getCookies())
                .thenReturn(new Cookie[]{JSESSIONID});

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            customerService.getCustomerID(request);
        });

        assertEquals("No matching 'customerData' cookie found!", exception.getMessage());
    }

    // Test that verifies that an illegalStateException gets thrown, when there are no cookies.
    @Test
    public void testGetCustomerID03() {
        when(request.getCookies())
                .thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            customerService.getCustomerID(request);
        });

        assertEquals("There are no cookies for this customer!", exception.getMessage());
    }

    // Test that verifies if the request is null, then throw IllegalArgumentException.
    @Test
    public void testGetCustomerID04() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomerID(null);
        });

        assertEquals("HttpServletRequest cannot be null!", exception.getMessage());
    }

    // Testing on getLegalAge method

    // Test that verifies that the correct legalAge gets returned, when a "customerData" cookie already exist.
    @Test
    public void testGetLegalAge01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false|new");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        String result = customerService.getLegalAge(request);

        assertEquals("false", result); // Tests if the result matches the expected result("false").
    }

    // Test that verifies if the request is null, then throw IllegalArgumentException.
    @Test
    public void testGetLegalAge02() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getLegalAge(null);
        });

        assertEquals("HttpServletRequest cannot be null!", exception.getMessage());
    }

    // Testing on ifCookieExist method

    // Test that verifies that if the 'customerData' cookie already exist, then return true.
    @Test
    public void testIfCookieExist01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false|new");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        boolean result = customerService.ifCookieExist(request);

        assertTrue(result);
    }

    // Test that verifies that if JSESSIONID is present but there is no 'customerData' cookie, then return false.
    @Test
    public void testIfCookieExist02() {
        Cookie JSESSIONID = new Cookie("JSESSIONID", "anotherSessionID");
        when(request.getCookies())
                .thenReturn(new Cookie[]{JSESSIONID});

        boolean result = customerService.ifCookieExist(request);

        assertFalse(result);
    }

    // Test that verifies if there are no cookies at all, then return false.
    @Test
    public void testIfCookieExist03() {
        when(request.getCookies())
                .thenReturn(null);

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

    // Testing on getCookieAge method

    // Test that verifies that the correct cookieAge gets returned, when a "customerData" cookie already exist.
    @Test
    public void testGetCookieAge01() {
        Cookie customerCookie = new Cookie("customerData", "testSessionID|false|new");
        when(request.getCookies())
                .thenReturn(new Cookie[]{customerCookie});

        String result = customerService.getCookieAge(request);

        assertEquals("new", result); // Tests if the result matches the expected result("new").
    }

    // Test that verifies if the request is null, then throw IllegalArgumentException.
    @Test
    public void testGetCookieAge02() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCookieAge(null);
        });

        assertEquals("HttpServletRequest cannot be null!", exception.getMessage());
    }
}

