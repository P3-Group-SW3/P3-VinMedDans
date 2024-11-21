package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Orders;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.OrderRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import com.vmd.vmdwebshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.View;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrderService {


    @Mock
    private View Error;


    @Mock
    private OrderLineRepository orderLineRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    List<Orders> orderList = new ArrayList<>() {};

    Orders order = null;



    @BeforeEach
    public void setUP(){
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        orderList.add((new Orders("g t","c","e@mail.c","+4599999999","ringevej 991","5050","hej")));
        //orderList.getLast().setID(Long.parseLong("1"));
        orderList.add((new Orders("g t","c","n@mail.c","+4599888888","farvel 991","5045","hey")));
        //orderList.getLast().setID(Long.parseLong("2"));
    }

    @Test
    public void TestGetorders(){

    }



}
