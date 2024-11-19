package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.order.*;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Orders;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.View;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestOrderService {


    @Mock
    private View Error;


    @Mock
    private OrderLineRepository orderLineRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    List<Orders> orderList = new ArrayList<>();
    List<OrderLine> orderLineList = new ArrayList<>();

    Orders order = null;

    OrderDto orderDto = null;

    OrderLine orderLine = null;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        orderList.add((new Orders("g t","c","e@mail.c","+4599999999","ringevej 991","5050","hej")));
        orderList.add((new Orders("g t","c","n@mail.c","+4599888888","farvel 991","5045","hey")));

        orderDto = mock(OrderDto.class);
        order = mock(Orders.class);
        orderLine = mock(OrderLine.class);

        orderLineList.add(orderLine);

    }

    //Test that asserts that when a list of orders isn't found in the database, an exception will be thrown
    @Test
    public void TestGetOrders01(){
        when(orderRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        OrdersNotFound newException = assertThrows(OrdersNotFound.class, ()->{ orderService.getAllOrders(); });
        System.out.println(newException.getMessage());
    }

    //Test that asserts that when a list of orders is found in the database, the list is not empty
    @Test
    public void TestGetOrders02(){
        when(orderRepository.findAll()).thenReturn(orderList);

        assertFalse(orderList.isEmpty());
        System.out.println(orderList);
    }

    //Test that asserts that when an order isn't found in the database, an exception will be thrown
    @Test
    public void TestGetOrderById01(){
        when(orderRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(orderList.get(1)));

        Orders newOrder = orderService.getOrderById(Long.parseLong("1"));

        assertEquals(orderList.get(1).getAdress(), newOrder.getAdress());
    }

    //Test that asserts that when an order isn't found in the database, an exception will be thrown
    @Test
    public void TestGetOrderById02(){
        when(orderRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());

        OrderNotFoundInDatbase newException = assertThrows(OrderNotFoundInDatbase.class, ()->{ orderService.getOrderById(Long.parseLong("1")); });

        assertEquals("The order with the id: 1 was not found", newException.getMessage());

    }


    //Test that asserts that given an Order object, and a new state, the state is changed.
    @Test
    public void TestChangeState01(){
        when(orderRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(orderList.get(1)));

        orderService.changeState(Long.parseLong("1"), 2);

        assertEquals("PACKED", orderList.get(1).getState().toString());
        System.out.println(orderList.get(1).getState().toString());
    }


    //Test that asserts that when the state is not updated, an exception will be thrown
    @Test
    public void TestChangeState02(){
        when(orderRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(orderList.get(1)));

        orderService.changeState(Long.parseLong("1"), 2);

        assertEquals("PACKED", orderList.get(1).getState().toString());
        System.out.println(orderList.get(1).getState().toString());

    }


    //Asserts that the ID of a new order created with the CreateOrderFromInfo() method matches the expected value.
    @Test
    public void TestCreateOrderFromInfo01(){
        when(orderDto.createOrderFromInfo()).thenReturn(order);
        when(orderRepository.save(any(Orders.class))).thenReturn(order);
        when(order.getID()).thenReturn(Long.parseLong("1"));
        when(order.getFullName()).thenReturn("Jens Peter");
        when(order.getMail()).thenReturn("a@b.com");
        when(orderLine.getOrderID()).thenReturn(Long.parseLong("1"));

        Orders newOrder = orderService.createOrderFromInfo(orderDto, orderLineList);

        assertEquals("1", newOrder.getID().toString());
    }

    //Asserts that if the ID of the order object is null, an exception will be thrown
    @Test
    public void TestCreateOrderFromInfo02(){
        when(orderDto.createOrderFromInfo()).thenReturn(order);
        when(orderRepository.save(any(Orders.class))).thenReturn(order);
        when(order.getID()).thenReturn(null);
        when(order.getFullName()).thenReturn("Jens Peter");
        when(order.getMail()).thenReturn("a@b.com");

        OrderNotSaved newException = assertThrows(OrderNotSaved.class, ()->{ orderService.createOrderFromInfo(orderDto, orderLineList); });

        assertEquals("The order was not created, customer: Jens Peter email: a@b.com", newException.getMessage());
    }


    //asserts that when the orderID of the orderline and the orderID, the program will throw an exception
    @Test
    public void TestCreateOrderFromInfo03(){
        when(orderDto.createOrderFromInfo()).thenReturn(order);
        when(orderRepository.save(any(Orders.class))).thenReturn(order);
        when(order.getID()).thenReturn(Long.parseLong("2"));
        when(order.getFullName()).thenReturn("Jens Peter");
        when(order.getMail()).thenReturn("a@b.com");
        when(orderLine.getOrderID()).thenReturn(Long.parseLong("3"));
        when(orderLine.getID()).thenReturn(Long.parseLong("1"));

        OrderlineNotAdded newException = assertThrows(OrderlineNotAdded.class, ()->{ orderService.createOrderFromInfo(orderDto, orderLineList); });
        assertEquals("orderline with id: 1 did not add the order ID of: 2", newException.getMessage());
    }

    //Test that asserts that an Order object is created, based on the Order Data Transfer Object.
    @Test
    public void TestOrderClass01(){
        OrderDto orderDto = new OrderDto();
        orderDto.setFirstName("Jens");
        orderDto.setLastName("Peter");
        orderDto.setEmail("a@b.com");
        orderDto.setPhone("12345678");
        orderDto.setAddress("vej 1");
        orderDto.setZipCode("2100");
        orderDto.setCity("København Ø");

        Orders order = orderDto.createOrderFromInfo();

        assertEquals("Jens Peter", order.getFullName());
    }





}
