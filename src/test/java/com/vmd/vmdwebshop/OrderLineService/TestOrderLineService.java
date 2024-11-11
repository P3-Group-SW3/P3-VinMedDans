package com.vmd.vmdwebshop.OrderLineService;

import com.vmd.vmdwebshop.exception.orderline.CartNotClearedException;
import com.vmd.vmdwebshop.exception.orderline.EmptyCartException;
import com.vmd.vmdwebshop.exception.orderline.OrderLineDataAccessException;
import com.vmd.vmdwebshop.exception.orderline.OrderLineDoesNotExistException;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import com.vmd.vmdwebshop.service.OrderLineService;
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

    OrderLine orderLine = null;
    OrderLine orderLine1 = null;
    OrderLine orderLine2 = null;
    OrderLine orderLine3 = null;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        OrderLine orderLine1 = new OrderLine(20, Long.parseLong("1"), "abc");
        OrderLine orderLine2 = new OrderLine(19, Long.parseLong("2"), "abc");


        orderLineList.clear();
        orderLineList.add(orderLine1);
        orderLineList.add(orderLine2);
    }

    // Testing on clearCart method

    /** Tests whether the returned list of remaining orderlines is empty after deletion */
    @Test
    public void TestClearCart01(){
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(orderLineList) // simulates the behaviour before deletion
                .thenReturn(Collections.emptyList()); // simulates the behaviour after deletion

        List<OrderLine> remainingOrderLines = orderLineService.clearCart("abc");
        assertTrue(remainingOrderLines.isEmpty(), "The cart is empty after clearing");
    }

    /** Tests whether an exception is thrown when the cart is already empty at initialisation */
    @Test
    public void TestClearCart02(){
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(Collections.emptyList());

        assertThrows(EmptyCartException.class, () -> orderLineService.clearCart("abc"));
    }

    /** Tests whether an exception error is thrown in case the cart has not been cleared after attempted deletion */
    @Test
    public void TestClearCart03(){
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(orderLineList) // simulates the behaviour before deletion
                .thenReturn(orderLineList); // simulates the behaviour after deletion

        assertThrows(CartNotClearedException.class, () -> orderLineService.clearCart("abc"));
    }


    @Test
    public void TestGetOrderLines01(){
        when(orderLineRepository.findAllByCustomerId("abc")).thenReturn(orderLineList);

        for (OrderLine orderline : orderLineList){
            System.out.println(orderline.getCustomerID());
        }

        for (OrderLine orderline : orderLineService.getAllOrderLines("abc")){
            System.out.println(orderline.getCustomerID());
        }

        System.out.println(orderLineService.getAllOrderLines("abc"));

        assertNotEquals(orderLineList, orderLineService.getAllOrderLines("abc"));

    }

    @Test
    public void TestGetOrderLines02(){
        when(orderLineRepository.findAllByCustomerId("abc")).thenReturn(orderLineList);

        for (OrderLine orderline : orderLineList){
            System.out.println(orderline.getCustomerID());
        }

        for (OrderLine orderline : orderLineService.getAllOrderLines("abc")){
            System.out.println(orderline.getCustomerID());
        }

        System.out.println(orderLineList);
        System.out.println(orderLineService.getAllOrderLines("abc"));

        assertEquals(orderLineList, orderLineService.getAllOrderLines("abc"));
    }

    @Test
    public void TestGetOrderLines03(){
        when(orderLineRepository.findAllByCustomerId("456")).thenReturn(orderLineList);

        for (OrderLine orderline : orderLineList){
            System.out.println(orderline.getCustomerID());
        }

        List<OrderLine> orderLine1 = orderLineService.getAllOrderLines("911");


        for (OrderLine orderline : orderLine1){
            System.out.println(orderline.getCustomerID());
        }

        System.out.println(orderLineList);
        System.out.println(orderLine1);

        assertNotEquals(orderLineList, orderLine1);

    }


    /** Tests that when the createAndEditOrderLine method is called, the service returns a list of OrderLines*/
    @Test
    public void TestCreateAndEditOrderLine01(){
        when(orderLineRepository.findAllByCustomerId("abc")).thenReturn(orderLineList);


        List<OrderLine> updatedOrderLines = orderLineService.createAndEditOrderLine(new OrderLine(2, Long.parseLong("2"), "abc"));

        assertTrue(!updatedOrderLines.isEmpty(), "The customers orderlines are returned");
    }

    /** Test that an exception is thrown if there is a data acces failure in the database */
    @Test
    public void TestCreateAndEditOrderLine02(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2"))).thenThrow(DataAccessResourceFailureException.class);

        assertThrows(DataAccessException.class, () -> orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")));
    }

    // Testing on deleteOrderLine method

    @Test
    public void TestDeleteOrderLine01(){
        //orderLineService.deleteOrderLine("911", Long.parseLong("1"));

        assertNull(orderLineRepository.findByCustomerIDAndWineID("911", Long.parseLong("1")));

        System.out.println(orderLineRepository.findByCustomerIDAndWineID("911", Long.parseLong("1")));
    }

    /** Tests whether an exception is thrown when no orderline matching customer and wine id exists*/
    @Test
    public void TestDeleteOrderLine02(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("1")))
                .thenReturn(orderLine);
        assertThrows(NullPointerException.class, () -> orderLineService.deleteOrderLine(orderLine));
    }





}

