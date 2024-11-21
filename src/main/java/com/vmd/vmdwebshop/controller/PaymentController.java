package com.vmd.vmdwebshop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${website.domain}")
    private String DOMAIN;

    @Value("${stripe.api.key}")
    private String stripeAPIKey;

        @PostMapping("/createPayment")
        public void createPayment() {


        }
}
