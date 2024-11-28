package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.orderline.*;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.OrderLineRepository;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Mock
    private WineService wineService;

    @InjectMocks
    private OrderLineService orderLineService;


    List<OrderLine> orderLineList = new ArrayList<>() {};

    OrderLine orderLine;
    OrderLine orderLine1;
    OrderLine orderLine2;

    Wine wine1;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        orderLine1 = new OrderLine(20, Long.parseLong("1"), "abc");
        orderLine2 = new OrderLine(19, Long.parseLong("2"), "abc");

        orderLineList.clear();
        orderLineList.add(orderLine1);
        orderLineList.add(orderLine2);

        wine1 = new Wine("description", "imgUrl", 200, 10, "name");
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
    public void TestGetAllOrderLines01(){
        when(orderLineRepository.findAllByCustomerId("abc")).thenReturn(orderLineList);

        List<OrderLine> orderLines = orderLineService.getAllOrderLines("abc");

        assertTrue(orderLines != null);
    }

    // Testing on createAndEditOrderLine method

    /** Test that when the createAndEditOrderLine method is called, the service returns a list of OrderLines for when
     * existingOrderline is not null */
    @Test
    public void TestCreateAndEditOrderLine01(){
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(orderLineList);
        when(wineService.getWineById(Long.parseLong("2")))
                .thenReturn(wine1);

        List<OrderLine> updatedOrderLines = orderLineService.createAndEditOrderLine(new OrderLine(2, Long.parseLong("2"), "abc"));

        assertFalse(updatedOrderLines.isEmpty(), "The customers orderlines are returned");
    }

    /** Test that when the createAndEditOrderLine method is called, the service returns a list of OrderLines for when
     * existingOrderline is null */
    @Test
    public void TestCreateAndEditOrderLine02(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")))
                .thenReturn(null);
        when(wineService.getWineById(Long.parseLong("2")))
                .thenReturn(wine1);
        when(orderLineRepository.findAllByCustomerId("abc"))
                .thenReturn(orderLineList);

        List<OrderLine> updatedOrderLines = orderLineService.createAndEditOrderLine(orderLine2);
        assertFalse(updatedOrderLines.isEmpty(), "The customers orderlines are returned");

    }

    /** Test that an exception is thrown if there is a data access failure in the database when tryijng to retrieve orderlines */
    @Test
    public void TestCreateAndEditOrderLine03(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")))
                .thenThrow(DataAccessResourceFailureException.class);

        assertThrows(DataAccessException.class, () -> orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")));
    }

    /** Test whether an exception is thrown when there is a failure to save the orderline to the database */
    @Test
    public void TestCreateAndEditOrderLine04(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")))
                .thenReturn(orderLine1);
        doThrow(DataAccessResourceFailureException.class)
                .when(orderLineRepository).save(orderLine1);

        assertThrows(OrderLineDataAccessException.class, () -> orderLineService.createAndEditOrderLine(orderLine1));
    }

    /** Test whether an exception is thrown when there is a failure to update the existing orderline */
    @Test
    public void TestCreateAndEditOrderLine05(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")))
                .thenReturn(orderLine1);
        doThrow(DataIntegrityViolationException.class)
                .when(orderLineRepository).save(orderLine1);

        assertThrows(OrderLineNotUpdatedException.class, () -> orderLineService.createAndEditOrderLine(orderLine1));
    }
    /** Test whether an exception is thrown if amount is set to a negative integer */
    @Test
    public void createAndEditOrderLine06(){
        when(orderLineRepository.findByCustomerIDAndWineID("abc", Long.parseLong("2")))
                .thenReturn(orderLine1);
        orderLine1.setAmount(0);
        assertThrows(IllegalArgumentException.class, () -> orderLineService.createAndEditOrderLine(orderLine1));
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

    //Test that asserts that when a wine has a higher stock than the amount in an orderline, the canBePurchased method will return true.
    @Test
    public void canBePurchased01(){
        Wine wine1 = new Wine("Vin", "URL", 189.0, 123, "Rødvin");
        Wine wine2 = new Wine("Rød", "URL", 189.0, 121, "Hvidvin");

        when(wineRepository.getById(Long.parseLong("1"))).thenReturn(wine1);
        when(wineRepository.getById(Long.parseLong("2"))).thenReturn(wine2);

        assertTrue(orderLineService.canBePurchased(orderLineList));
    }


    //Test that asserts that when one wine has a lower stock than the amount in an orderline, an exception will be thrown.
    //The error message displays the wine that cannot be purchased.
    @Test
    public void canBePurchased03(){
        Wine wine1 = mock(Wine.class);
        Wine wine2 = mock(Wine.class);

        List<OrderLine> newOrderLineList = new ArrayList<>();
        newOrderLineList.add(new OrderLine(1, Long.parseLong("1"), "1"));
        newOrderLineList.add(new OrderLine(1, Long.parseLong("2"), "1"));

        when(wine1.getID()).thenReturn(Long.parseLong("1"));
        when(wine2.getID()).thenReturn(Long.parseLong("2"));

        when(wineRepository.getById(Long.parseLong("1"))).thenReturn(wine1);
        when(wineRepository.getById(Long.parseLong("2"))).thenReturn(wine2);

        when(wine1.canBePurchased(anyInt())).thenReturn(true);
        when(wine2.canBePurchased(anyInt())).thenReturn(false);

        OrderLineCannotBePurchased newException = assertThrows(OrderLineCannotBePurchased.class, ()->{ orderLineService.canBePurchased(orderLineList); });

        System.out.println(newException.getMessage());
        assertEquals("There is not enough stock for wine(s): ID:2", newException.getMessage());
    }


}

