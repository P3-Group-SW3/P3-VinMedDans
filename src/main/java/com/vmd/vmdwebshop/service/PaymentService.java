package com.vmd.vmdwebshop.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.vmd.vmdwebshop.DTO.OrderDto;
import com.vmd.vmdwebshop.model.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${website.domain}")
    private String DOMAIN;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook}")
    private String endpointSecret;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderLineService orderLineService;

    @Autowired
    private OrderService orderService;

    public Map<String, String> createCheckoutSession(OrderDto orderDto, HttpServletRequest request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String serviceId = customerService.getCustomerID(request);
        List<OrderLine> orderLines = orderLineService.getAllOrderLines(serviceId);

        double numL = orderLineService.calculateOrderLines(orderLines) * 100;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(DOMAIN + "/payment?success=true&session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(DOMAIN + "/payment?canceled=true")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("DKK")
                                                .setUnitAmount((long) numL) // Price in DKK
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Wine")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build())
                .build();
        Session session = Session.create(params);
        Map<String, String> response = new HashMap<>();
        response.put("url", session.getUrl());
        return response;
    }

    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        String payload;

        try {
            payload = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid payload");
        }
        String sigHeader = request.getHeader("Stripe-Signature");
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
        }
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null) {
                // Payment was successful
                return ResponseEntity.ok("Payment successful");
            }
        }
        return ResponseEntity.ok("Payment not completed");
    }

    public Map<String, String> getPaymentStatus(String sessionId) {
        Stripe.apiKey = stripeSecretKey;
        try {
            Session session = Session.retrieve(sessionId);
            String paymentStatus = session.getPaymentStatus();
            Map<String, String> response = new HashMap<>();
            response.put("status", paymentStatus);
            return response;
        } catch (StripeException e) {
            return null;
        }
    }
}