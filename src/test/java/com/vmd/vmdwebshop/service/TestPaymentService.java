package com.vmd.vmdwebshop.service;

import com.stripe.exception.ApiException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.vmd.vmdwebshop.DTO.OrderDto;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Orders;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestPaymentService {

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderLineService orderLineService;

    @Mock
    private OrderService orderService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCheckoutSession_success() throws StripeException {
        when(customerService.getCustomerID(request)).thenReturn("customerId");
        when(orderLineService.getAllOrderLines("customerId")).thenReturn(List.of(new OrderLine()));
        when(orderLineService.calculateOrderLines(anyList())).thenReturn(100.0);
        when(orderService.createOrderFromInfo(any(), anyList(), anyString())).thenReturn(new Orders());

        Session session = mock(Session.class);
        when(session.getUrl()).thenReturn("http://example.com");

        try (MockedStatic<Session> mockedSession = mockStatic(Session.class)) {
            mockedSession.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(session);

            Map<String, String> response = paymentService.createCheckoutSession(new OrderDto(), request);

            assertNotNull(response);
            assertTrue(response.containsKey("url"));
            assertEquals("http://example.com", response.get("url"));
        }
    }

    /*@Test
    void handleStripeWebhook_validPayload() throws IOException, StripeException {
        BufferedReader reader = new BufferedReader(new StringReader("payload"));
        when(request.getReader()).thenReturn(reader);
        when(request.getHeader("Stripe-Signature")).thenReturn("signature");
        when(orderService.getOrderBySessionID(anyString())).thenReturn(new Orders());

        // Ensure the Event class is correctly imported and used
        com.stripe.model.Event mockEvent = mock(com.stripe.model.Event.class);
        try (MockedStatic<Webhook> mockedWebhook = mockStatic(Webhook.class)) {
            mockedWebhook.when(() -> Webhook.constructEvent(anyString(), anyString(), anyString()))
                         .thenReturn(mockEvent);

            ResponseEntity<String> response = paymentService.handleStripeWebhook(request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Payment successful and order state updated", response.getBody());
        }
    }*/

    /*@Test
    void handleStripeWebhook_invalidSignature() throws IOException, StripeException {
        BufferedReader reader = new BufferedReader(new StringReader("payload"));
        when(request.getReader()).thenReturn(reader);
        when(request.getHeader("Stripe-Signature")).thenReturn("invalid_signature");

        ResponseEntity<String> response = paymentService.handleStripeWebhook(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid signature", response.getBody());
    }*/

    @Test
    void getPaymentStatus_success() throws StripeException {
        Session session = mock(Session.class);
        when(session.getPaymentStatus()).thenReturn("paid");

        try (MockedStatic<Session> mockedSession = mockStatic(Session.class)) {
            mockedSession.when(() -> Session.retrieve(anyString())).thenReturn(session);

            Map<String, String> response = paymentService.getPaymentStatus("sessionId");

            assertNotNull(response);
            assertTrue(response.containsKey("status"));
            assertEquals("paid", response.get("status"));
        }
    }

    @Test
    void getPaymentStatus_failure() throws StripeException {
        try (MockedStatic<Session> mockedSession = mockStatic(Session.class)) {
            mockedSession.when(() -> Session.retrieve(anyString())).thenThrow(new ApiException("error", null, null, 0, null));

            Map<String, String> response = paymentService.getPaymentStatus("sessionId");

            assertNull(response);
        }
    }
}