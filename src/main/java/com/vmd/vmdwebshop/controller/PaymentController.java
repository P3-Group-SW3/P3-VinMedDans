package com.vmd.vmdwebshop.controller;

import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import com.vmd.vmdwebshop.service.OrderLineService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${website.domain}")
    private String DOMAIN;

    @Value("${stripe.PublicKey}")
    private String stripeAPIKey;

    private final WineRepository wineRepository;
    private final OrderLineService orderLineService;

    public PaymentController(WineRepository wineRepository, OrderLineService orderLineService) {
        this.wineRepository = wineRepository;
        this.orderLineService = orderLineService;
    }

    @PostMapping("/createPayment")
    public Map<String, String> createPayment(@CookieValue(value = "cookieId", defaultValue = "") String cookieID) throws StripeException {
        Stripe.apiKey = stripeAPIKey;

        // Fetch order lines for the user identified by the cookie
        List<OrderLine> orderLines = orderLineService.getAllOrderLines(cookieID);

        // Create line items for each order line
        SessionCreateParams.LineItem[] lineItems = orderLines.stream().map(orderLine -> {
            Wine wine = wineRepository.findById(orderLine.getWineId()).orElseThrow();
            return SessionCreateParams.LineItem.builder()
                    .setQuantity(orderLine.getQuantity())
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("dkk")
                                    .setUnitAmount((long) (wine.getPrice() * 100)) // Convert price to cents
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(wine.getName())
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();
        }).toArray(SessionCreateParams.LineItem[]::new);

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(DOMAIN + "/success")
                .setCancelUrl(DOMAIN + "/cancel")
                .addAllLineItem(List.of(lineItems))
                .build();

        Session session = Session.create(params);

        Map<String, String> map = new HashMap<>();
        map.put("clientSecret", session.getId());

        return map;
    }
}