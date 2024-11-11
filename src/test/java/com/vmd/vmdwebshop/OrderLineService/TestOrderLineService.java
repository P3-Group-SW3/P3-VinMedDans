package com.vmd.vmdwebshop.OrderLineService;

import com.vmd.vmdwebshop.exception.orderline.CartNotClearedException;
import com.vmd.vmdwebshop.exception.orderline.EmptyCartException;
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


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        orderLineList.clear();
        orderLineList.add(new OrderLine(20, Long.parseLong("1"), "abc"));
        orderLineList.add(new OrderLine(12, Long.parseLong("1"), "911"));
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

    @Test
    public void TestCreateAndEditOrderLine(){
        OrderLine newOrderLine = new OrderLine(15, Long.parseLong("2"), "abc");

        System.out.println(orderLineService.getAllOrderLines("911"));

        System.out.println(orderLineList);

        List<OrderLine> orderLines1 = orderLineService.createAndEditOrderLine(newOrderLine);

        System.out.println(orderLines1);
        System.out.println(orderLineList);


        System.out.println(orderLineList.getFirst().getAmount());
        System.out.println(orderLineList.getLast().getAmount());

        assertTrue(orderLines1.contains(newOrderLine));

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

