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
import com.vmd.vmdwebshop.model.Orders;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.io.IOUtils;

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

    /**
     * Create a checkout session
     *
     * @param orderDto The order data
     * @param request The request
     * @return A response entity with the result of the checkout session creation
     * @throws StripeException If an error occurs during the checkout session creation
     */
    public Map<String, String> createCheckoutSession(OrderDto orderDto, HttpServletRequest request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String serviceId = customerService.getCustomerID(request);
        List<OrderLine> orderLines = orderLineService.getAllOrderLines(serviceId);

        double numL = orderLineService.calculateOrderLines(orderLines) * 100;

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(DOMAIN + "/order?success=true&session_id={CHECKOUT_SESSION_ID}")
            .setCustomerEmail(null)
            .addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("DKK")
                                            .setUnitAmount((long) numL) // Price in DKK
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("VMD Webshop")
                                                            .build()
                                            ).build()
                            ).build()
            ).build();

        Session session = Session.create(params);
        Map<String, String> response = new HashMap<>();
        response.put("url", session.getUrl());

        orderService.createOrderFromInfo(orderDto, orderLines, session.getId());

        return response;
    }


    /**
     * Handle a Stripe webhook
     *
     * @param request The request
     * @return A response entity with the result of the webhook handling
     */
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        String payload;

        try { // Check if the payload is valid, if not return bad request
            payload = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid payload");
        }

        String sigHeader = request.getHeader("Stripe-Signature");
        Event event;

        try { // Check if the signature is valid, if not return unauthorized
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
        }

        // Handle the event if it is a checkout session completed event and update the order state to confirmed
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            if (session != null) {
                Orders order = orderService.getOrderBySessionID(session.getId());
                orderService.changeState(order.getID(), Orders.State.CONFIRMED.ordinal());
                return ResponseEntity.ok("Payment successful and order state updated");
            }
        }

        return ResponseEntity.ok("Payment not completed");
    }
}
