package com.vmd.vmdwebshop.controller;

import com.stripe.exception.StripeException;
import com.vmd.vmdwebshop.DTO.OrderDto;
import com.vmd.vmdwebshop.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}