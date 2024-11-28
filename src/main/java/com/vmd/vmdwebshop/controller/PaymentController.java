package com.vmd.vmdwebshop.controller;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${website.domain}")
    private String DOMAIN;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook}")
    private String endpointSecret;

 @PostMapping("/create-checkout-session")
public ResponseEntity<Map<String, String>> createCheckoutSession() throws StripeException {
    Stripe.apiKey = stripeSecretKey;

    SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(DOMAIN + "/payment?success=true&session_id={CHECKOUT_SESSION_ID}")
            .setCancelUrl(DOMAIN + "/payment?canceled=true")
            .addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount(500L) // Price in cents (5.00 USD)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("name of the product")
                                                            .build()
                                            )
                                            .build()
                            )
                            .build())
            .build();

    Session session = Session.create(params);

    Map<String, String> response = new HashMap<>();
    response.put("url", session.getUrl());

    return ResponseEntity.ok(response);
}

    @PostMapping("/webhook")
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

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getPaymentStatus(@RequestParam String sessionId) {
        Stripe.apiKey = stripeSecretKey;

        try {
            Session session = Session.retrieve(sessionId);
            String paymentStatus = session.getPaymentStatus();

            Map<String, String> response = new HashMap<>();
            response.put("status", paymentStatus);

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}