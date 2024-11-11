package com.vmd.vmdwebshop.OrderLineService;

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

        orderLineList.add(new OrderLine(11, Long.parseLong("2"), "911"));
        orderLineList.add(new OrderLine(12, Long.parseLong("1"), "911"));
    }

    @Test
    public void TestClearCart01(){
        List<OrderLine> orderLines = orderLineService.clearCart("ddd");
        assertTrue(orderLines.isEmpty(), "The cart is empty after clearing");
    }


    @Test
    public void TestGetOrderLines01(){
        when(orderLineRepository.findAllByCustomerId("911")).thenReturn(orderLineList);

        for (OrderLine orderline : orderLineList){
            System.out.println(orderline.getCustomerID());
        }

        for (OrderLine orderline : orderLineService.getAllOrderLines("1")){
            System.out.println(orderline.getCustomerID());
        }

        System.out.println(orderLineService.getAllOrderLines("1"));

        assertNotEquals(orderLineList, orderLineService.getAllOrderLines("1"));

    }

    @Test
    public void TestGetOrderLines02(){
        when(orderLineRepository.findAllByCustomerId("911")).thenReturn(orderLineList);

        for (OrderLine orderline : orderLineList){
            System.out.println(orderline.getCustomerID());
        }

        for (OrderLine orderline : orderLineService.getAllOrderLines("911")){
            System.out.println(orderline.getCustomerID());
        }

        System.out.println(orderLineList);
        System.out.println(orderLineService.getAllOrderLines("911"));

        assertEquals(orderLineList, orderLineService.getAllOrderLines("911"));
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
        OrderLine newOrderLine = new OrderLine(15, Long.parseLong("2"), "911");

        System.out.println(orderLineService.getAllOrderLines("911"));

        System.out.println(orderLineList);

        List<OrderLine> orderLines1 = orderLineService.createAndEditOrderLine(newOrderLine);

        System.out.println(orderLines1);
        System.out.println(orderLineList);


        System.out.println(orderLineList.getFirst().getAmount());
        System.out.println(orderLineList.getLast().getAmount());

        assertTrue(orderLines1.contains(newOrderLine));

    }

    @Test
    public void TestDeleteOrderLine01(){
        //orderLineService.deleteOrderLine("911", Long.parseLong("1"));

        assertNull(orderLineRepository.findByCustomerIDAndWineID("911", Long.parseLong("1")));

        System.out.println(orderLineRepository.findByCustomerIDAndWineID("911", Long.parseLong("1")));
    }

    @Test
    public void TestDeleteOrderLine02(){
    }





}

