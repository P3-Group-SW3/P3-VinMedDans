package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.wine.WineDataAccessException;
import com.vmd.vmdwebshop.exception.wine.WineNotFoundException;
import com.vmd.vmdwebshop.exception.wine.WineNotUpdatedException;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.View;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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

    Wine wine = null;

    List<Wine> wineList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        wine = mock(Wine.class);
        wine1 = new Wine("Vin", "URL", 189.0, 123, "Rødvin");
        wineList.add(wine1);
    }


    //asserts that when an error occurs in the database, an exception will be thrown
    @Test
    public void TestCreateAndEditWine01() {
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());
        when(wineRepository.save(wine)).thenThrow(new DataIntegrityViolationException("hej med dig"));

        WineNotUpdatedException newException = assertThrows(WineNotUpdatedException.class, ()->{ wineService.createAndEdit(wine, Long.parseLong("1")); });

        System.out.println(newException.getMessage());
        assertEquals("Failed to update wine", newException.getMessage());
    }

    //asserts that when an error occurs in the database, an exception will be thrown
    @Test
    public void TestCreateAndEditWine05() {
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());
        when(wineRepository.save(wine)).thenThrow(new DataAccessException("Database access error") {});

        WineDataAccessException newException = assertThrows(WineDataAccessException.class, ()->{ wineService.createAndEdit(wine, Long.parseLong("1")); });

        System.out.println(newException.getMessage());
        assertEquals("Failed to save wine to the database", newException.getMessage());
    }


    //TODO, skal opdateres efter wineService findByMetoden er opdateret!!!!
    //asserts that when an error occurs in the database, an exception will be thrown
    @Test
    public void TestCreateAndEditWine06() {
        when(wineRepository.findById(Long.parseLong("1"))).thenThrow(new DataAccessException("Database access error") {});

        WineDataAccessException newException = assertThrows(WineDataAccessException.class, ()->{ wineService.createAndEdit(wine, Long.parseLong("1")); });

        System.out.println(newException.getMessage());
        assertEquals("Failed to retrieve the wine from the database", newException.getMessage());
    }

    //asserts that when a wine is found in the database, the amountLeft attribute will be updated.
    @Test
    public void TestCreateAndEditWine02() {
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(wine));
        when(wineRepository.findAll()).thenReturn(wineList);

        System.out.println(wine.getAmountLeft());

        List<Wine> newList = wineService.createAndEdit(wine1, Long.parseLong("1"));
        Wine updatedWine = newList.getFirst();

        assertEquals(123, updatedWine.getAmountLeft());
        assertFalse(newList.isEmpty());
    }

    //asserts that even when a wine is not found in the database, the wine entity will be saved.
    @Test
    public void TestCreateAndEditWine03() {
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());
        when(wineRepository.findAll()).thenReturn(wineList);
        when(wineRepository.save(any(Wine.class))).thenReturn(wine1);

        System.out.println(wine1.getAmountLeft());

        List<Wine> newList = wineService.createAndEdit(wine1, Long.parseLong("1"));
        Wine updatedWine = newList.getFirst();

        assertEquals(123, updatedWine.getAmountLeft());
        assertFalse(newList.isEmpty());
    }

    //Asserts that when a wine is not found in the database, an exception will be thrown, as no objects can be deleted.
    @Test
    public void TestDeleteWine(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.delete(Long.parseLong("1")); });
        assertEquals("Wine does not exist in the database", newException.getMessage());
        System.out.println(newException.getMessage());
    }

    //Asserts that when a wine is not found, an exception will be thrown
    @Test
    public void TestGetWineById01(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.getWineById(Long.parseLong("1")); });

        assertEquals("The wine was not found", newException.getMessage());
        System.out.println(newException.getMessage());
    }

    //Asserts that when a wine is not found, the object is not null
    @Test void TestGetWineById02(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(wine1));

        Wine existingWine = wineService.getWineById(Long.parseLong("1"));

        assertNotNull(existingWine);
    }

    //Asserts that when no wines are found, an exception will be thrown
    @Test void TestGetList01(){
        when(wineRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.getAll(); });

        assertEquals("No wines were found in the database", newException.getMessage());
        System.out.println(newException.getMessage());

    }

    //Asserts that when no wines are found, an exception will be thrown
    @Test void TestGetList02(){
        when(wineRepository.findAll()).thenReturn(wineList);

        List<Wine> allWines = wineService.getAll();

        assertFalse(allWines.isEmpty());
        System.out.println(allWines);
    }


}

