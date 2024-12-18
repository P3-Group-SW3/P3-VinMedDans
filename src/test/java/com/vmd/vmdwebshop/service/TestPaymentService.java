package com.vmd.vmdwebshop.service;

import com.stripe.exception.ApiException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vmd.vmdwebshop.DTO.OrderDto;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Orders;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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

    // Testing on createCheckoutSession_success method

    @Test
    void createCheckoutSession_success() throws StripeException {
        when(customerService.getCustomerID(request))
                .thenReturn("customerId");
        when(orderLineService.getAllOrderLines("customerId"))
                .thenReturn(List.of(new OrderLine()));
        when(orderLineService.calculateOrderLines(anyList()))
                .thenReturn(100.0);
        when(orderService.createOrderFromInfo(any(), anyList(), anyString()))
                .thenReturn(new Orders());

        Session session = mock(Session.class);
        when(session.getUrl())
                .thenReturn("http://example.com");

        try (MockedStatic<Session> mockedSession = mockStatic(Session.class)) {
            mockedSession.when(() -> Session.create(any(SessionCreateParams.class)))
                    .thenReturn(session);

            Map<String, String> response = paymentService.createCheckoutSession(new OrderDto(), request);

            assertNotNull(response);
            assertTrue(response.containsKey("url"));
            assertEquals("http://example.com", response.get("url"));
        }
    }
}
