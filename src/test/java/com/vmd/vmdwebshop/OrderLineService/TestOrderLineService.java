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

        orderLineList.add(new OrderLine(11, Long.parseLong("911"), Long.parseLong("2")));
        orderLineList.add(new OrderLine(12, Long.parseLong("911"), Long.parseLong("1")));

    }

    @Test
    public void TestClearCart01(){ assertTrue(orderLineService.clearCart(Long.parseLong("789"))); }

    @Test
    public void TestClearCart02(){ assertTrue(orderLineService.clearCart(Long.parseLong("199"))); }

    @Test
    public void TestClearCart03(){ assertTrue(orderLineService.clearCart(Long.parseLong("123"))); }

    @Test
    public void TestClearCart05(){ assertTrue(orderLineService.clearCart(Long.parseLong("0"))); }

    @Test
    public void TestGetOrderLines01(){
        when(orderLineRepository.findAllByCustomerId(Long.parseLong("911"))).thenReturn(orderLineList);

        assertEquals(orderLineList, orderLineService.getAllOrderLines(Long.parseLong("911")));

        System.out.println(orderLineList);

        System.out.println(orderLineService.getAllOrderLines(Long.parseLong("911")));
    }

    @Test
    public void TestGetOrderLines02(){
        when(orderLineRepository.findAllByCustomerId(Long.parseLong("123"))).thenReturn(orderLineList);

        assertNotEquals(orderLineList, orderLineService.getAllOrderLines(Long.parseLong("911")));

        System.out.println(orderLineList);

        System.out.println(orderLineService.getAllOrderLines(Long.parseLong("911")));
    }

    @Test
    public void TestGetOrderLines03(){
        when(orderLineRepository.findAllByCustomerId(Long.parseLong("0"))).thenReturn(orderLineList);

        assertNotEquals(orderLineList, orderLineService.getAllOrderLines(Long.parseLong("123")));

        System.out.println(orderLineList);

        System.out.println(orderLineService.getAllOrderLines(Long.parseLong("123")));

    }

    @Test
    public void TestCreateAndEditOrderLine(){
        OrderLine newOrderLine = new OrderLine(15, Long.parseLong("911"), Long.parseLong("2"));

        orderLineService.createAndEditOrderLine(newOrderLine);

        assertEquals(newOrderLine, orderLineRepository.findByCustomerIDAndWineID(Long.parseLong("911"), Long.parseLong("2")));

        System.out.println(newOrderLine);

        System.out.println(orderLineRepository.findByCustomerIDAndWineID(Long.parseLong("911"), Long.parseLong("2")));
    }

    @Test
    public void TestDeleteOrderLine01(){
        orderLineService.deleteOrderLine(Long.parseLong("1"), Long.parseLong("911"));

        assertNull(orderLineRepository.findByCustomerIDAndWineID(Long.parseLong("911"), Long.parseLong("1")));

        System.out.println(orderLineRepository.findByCustomerIDAndWineID(Long.parseLong("911"), Long.parseLong("1")));
    }

    @Test
    public void TestDeleteOrderLine02(){
    }





}

