package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.wine.WineDataAccessException;
import com.vmd.vmdwebshop.exception.wine.WineNotFoundException;
import com.vmd.vmdwebshop.exception.wine.WineNotUpdatedException;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Wine;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestWineService {

    @Mock
    private View Error;

    @Mock
    private WineRepository wineRepository;


    @InjectMocks
    private WineService wineService;

    Wine wine1 = null;
    Wine wine2 = null;
    List<Wine> wineList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        wine1 = new Wine("Vin", "URL", 189.0, 123, "Rødvin");
        wine2 = new Wine("Rød", "URL", 189.0, 121, "Hvidvin");
        wineList.add(wine1);
        wineList.add(wine2);
    }

    @Test
    public void TestCreateAndEditWine01() {
        when(wineRepository.findById(Long.parseLong("99"))).thenThrow( new DataAccessException(""){});

        WineDataAccessException newException = assertThrows(WineDataAccessException.class, () -> { wineService.createAndEdit(wine1, Long.parseLong("99")); });
        assertEquals("Failed to retrieve the wine from the database", newException.getMessage());
        System.out.println(newException.getMessage());
    }

    @Test
    public void TestDeleteWine(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.delete(Long.parseLong("1")); });
        assertEquals("Wine does not exist in the database", newException.getMessage());
        System.out.println(newException.getMessage());
    }

    @Test
    public void TestGetWineByID01(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.getWineById(Long.parseLong("1")); });
        assertEquals("The wine was not found", newException.getMessage());
        System.out.println(newException.getMessage());
    }

    @Test
    public void TestGetWineByID02(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(wine1));

        Wine existingWine = wineService.getWineById(Long.parseLong("1"));

        assertNotNull(existingWine);
    }

    @Test
    public void TestGetList01(){
        when(wineRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.getAll(); });
        assertEquals("No wines were found in the database", newException.getMessage());
        System.out.println(newException.getMessage());

    }

    @Test
    public void TestGetList02(){
        when(wineRepository.findAll()).thenReturn(wineList);

        List<Wine> allWines = wineService.getAll();

        assertTrue(!allWines.isEmpty(), "Wines found");
        System.out.println(allWines);
    }

    //test that asserts that a new wines activeState is true by default, and the setActiveState reverses the state.
    @Test
    public void TestSetActiveState01(){
        Wine wine = new Wine(){};

        System.out.println(wine.getActiveState());
        wine.changeActiveState();

        assertFalse(wine.getActiveState());
    }

    //Test that asserts that an exception is thrown, if a wine does not exist
    @Test
    public void TestSetActiveState02(){
        when(wineRepository.findById(Long.parseLong("1"))).thenThrow( new DataAccessException(""){});

        WineDataAccessException newException = assertThrows(WineDataAccessException.class, ()->{ wineService.changeActiveState("1"); });

    }

    //Asserts that the canBePurchased method returns true if the amount purchased is lower than the stock
    @Test
    public void TestCanBePurchased01(){
        assertTrue(wine1.canBePurchased(1));
    }

    //Asserts that the canBePurchased method returns false if the amount purchased is higher than the stock
    @Test
    public void TestCanBePurchased02(){
        assertFalse(wine1.canBePurchased(1000));
    }

    @Test
    public void TestUpdateStockFromOrder01(){
        Wine wine1 = new Wine("Vin", "URL", 189.0, 123, "Rødvin");
        Wine wine2 = new Wine("Rød", "URL", 189.0, 121, "Hvidvin");

        List<OrderLine> newOrderLineList = new ArrayList<>();
        newOrderLineList.add(new OrderLine(1, Long.parseLong("1"), "1"));
        newOrderLineList.add(new OrderLine(1, Long.parseLong("2"), "1"));

        when(wineRepository.getById(Long.parseLong("1"))).thenReturn(wine1);
        when(wineRepository.getById(Long.parseLong("2"))).thenReturn(wine2);

        wineService.updateStockFromOrder(newOrderLineList);

        assertEquals(122, wine1.getAmountLeft());
        assertEquals(120, wine2.getAmountLeft());
    }

    @Test
    public void TestUpdateStockFromOrder02(){
        Wine wine1 = new Wine("Vin", "URL", 189.0, 123, "Rødvin");
        Wine wine2 = new Wine("Rød", "URL", 189.0, 121, "Hvidvin");

        List<OrderLine> newOrderLineList = new ArrayList<>();
        newOrderLineList.add(new OrderLine(1, Long.parseLong("1"), "1"));
        newOrderLineList.add(new OrderLine(1, Long.parseLong("2"), "1"));

        when(wineRepository.getById(Long.parseLong("1"))).thenThrow(new DataAccessException(""){});
        when(wineRepository.getById(Long.parseLong("2"))).thenReturn(wine2);

        WineDataAccessException newException = assertThrows(WineDataAccessException.class, ()->{ wineService.updateStockFromOrder(newOrderLineList); });
        System.out.println(newException.getMessage());

        assertEquals("The wine was not retrieved from the database", newException.getMessage());

    }

    @Test
    public void TestUpdateStockFromOrder03(){
        Wine wine1 = mock(Wine.class);
        Wine wine2 = new Wine("Rød", "URL", 189.0, 121, "Hvidvin");

        List<OrderLine> newOrderLineList = new ArrayList<>();
        newOrderLineList.add(new OrderLine(1, Long.parseLong("1"), "1"));
        newOrderLineList.add(new OrderLine(1, Long.parseLong("2"), "1"));

        when(wineRepository.getById(Long.parseLong("1"))).thenReturn(wine1);
        when(wineRepository.getById(Long.parseLong("2"))).thenReturn(wine2);

        when(wineRepository.save(wine1)).thenThrow(new DataIntegrityViolationException(""){});

        WineNotUpdatedException newException = assertThrows(WineNotUpdatedException.class, ()->{ wineService.updateStockFromOrder(newOrderLineList); });
        System.out.println(newException.getMessage());

        assertEquals("The stock of the wine was not updated", newException.getMessage());
    }

}

