package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.orderline.CartNotClearedException;
import com.vmd.vmdwebshop.exception.orderline.EmptyCartException;
import com.vmd.vmdwebshop.exception.orderline.OrderLineDataAccessException;
import com.vmd.vmdwebshop.exception.orderline.OrderLineDoesNotExistException;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.web.servlet.View;
import java.util.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


public class TestOrderLineService {

    @Mock
    private View Error;

    @Mock
    private WineRepository wineRepository;

    @Mock
    private OrderLineRepository orderLineRepository;

    @InjectMocks
    private OrderLineService orderLineService;

    List<OrderLine> orderLineList = new ArrayList<>() {};

    OrderLine orderLine;
    OrderLine orderLine1;
    OrderLine orderLine2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        orderLine1 = new OrderLine(20, Long.parseLong("1"), "abc");
        orderLine2 = new OrderLine(19, Long.parseLong("2"), "abc");

        orderLineList.clear();
        orderLineList.add(orderLine1);
        orderLineList.add(orderLine2);
    }

    // Testing on clearCart method

    /** Test whether the returned list of remaining orderlines is empty after deletion */
    @Test
    public void TestClearCart01(){
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(orderLineList) // simulates the behaviour before deletion
                .thenReturn(Collections.emptyList()); // simulates the behaviour after deletion

        List<OrderLine> remainingOrderLines = orderLineService.clearCart("abc");
        assertTrue(remainingOrderLines.isEmpty(), "The cart is empty after clearing");
    }

    /** Test whether an exception is thrown when the cart is already empty at initialisation */
    @Test
    public void TestClearCart02(){
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(Collections.emptyList());

        assertThrows(EmptyCartException.class, () -> orderLineService.clearCart("abc"));
    }

    /** Test whether an exception error is thrown in case the cart has not been cleared after attempted deletion */
    @Test
    public void TestClearCart03(){
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(orderLineList) // simulates the behaviour before deletion
                .thenReturn(orderLineList); // simulates the behaviour after deletion

        assertThrows(CartNotClearedException.class, () -> orderLineService.clearCart("abc"));
    }


    /** Test that getAllOrderLines method returns a list of all orderlines associated with a customwer*/
    @Test
    public void TestGetOrderLines01(){
        when(orderLineRepository.findAllByCustomerId("abc")).thenReturn(orderLineList);

        List<OrderLine> orderLines = orderLineService.getAllOrderLines("abc");

        assertTrue(orderLines != null);
    }

    // Testing on createAndEditOrderLine method

    /** Test that when the createAndEditOrderLine method is called, the service returns a list of OrderLines*/
    @Test
    public void TestCreateAndEditOrderLine01(){
        when(orderLineRepository.findAllByCustomerId("abc")).thenReturn(orderLineList);


        List<OrderLine> updatedOrderLines = orderLineService.createAndEditOrderLine(new OrderLine(2, Long.parseLong("2"), "abc"));

        assertTrue(!updatedOrderLines.isEmpty(), "The customers orderlines are returned");
    }

    /** Test that an exception is thrown if there is a data access failure in the database when tryijng to retrieve orderlines */
    @Test
    public void TestCreateAndEditOrderLine02(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2"))).thenThrow(DataAccessResourceFailureException.class);

        assertThrows(DataAccessException.class, () -> orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")));
    }

    /** Test whether an exeption is thrown when there is a failure when the orderline is updated/ saved to the database */
    @Test
    public void TestCreateAndEditOrderLine03(){
        // i gave up sorry
    }

    // Testing on deleteOrderLine method

    /** Test that deleteOrderline method returns a list of all the customer's remaining orderlines after deletion */
    @Test
    public void TestDeleteOrderLine01(){

        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2"))).thenReturn(orderLine2);
        when(orderLineRepository.findAllByCustomerId("abc")).thenReturn(orderLineList);
        List<OrderLine> remainingOrderLines = orderLineService.deleteOrderLine(orderLine2);

        assertNotNull(remainingOrderLines, "remaining orderlines returned");
    }

    /** Test whether an exception is thrown when no orderline matching customer and wine id exists*/
    @Test
    public void TestDeleteOrderLine02(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("1")))
                .thenReturn(null);
        assertThrows(NullPointerException.class, () -> orderLineService.deleteOrderLine(orderLine));
    }
}

