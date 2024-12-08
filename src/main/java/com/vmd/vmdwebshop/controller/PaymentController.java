package com.vmd.vmdwebshop.controller;

import com.stripe.exception.StripeException;
import com.vmd.vmdwebshop.DTO.OrderDto;
import com.vmd.vmdwebshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody OrderDto orderDto, HttpServletRequest request) throws StripeException {
        Map<String, String> response = paymentService.createCheckoutSession(orderDto, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        return paymentService.handleStripeWebhook(request);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getPaymentStatus(@RequestParam String sessionId) {
        Map<String, String> response = paymentService.getPaymentStatus(sessionId);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}