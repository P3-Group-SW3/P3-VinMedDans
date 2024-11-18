package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.exception.wine.WineNotFoundException;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.View;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        when(wineRepository.findById(Long.parseLong("99"))).thenReturn(Optional.empty());

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, () -> { wineService.createAndEdit(wine1, Long.parseLong("99")); });
        assertEquals("The Wine was not updated", newException.getMessage());
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

    @Test void TestGetWineByID02(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.ofNullable(wine1));

        Wine existingWine = wineService.getWineById(Long.parseLong("1"));

        assertNotNull(existingWine);
    }

    @Test void TestGetList01(){
        when(wineRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.getAll(); });
        assertEquals("No wines were found in the database", newException.getMessage());
        System.out.println(newException.getMessage());

    }

    @Test void TestGetList02(){
        when(wineRepository.findAll()).thenReturn(wineList);

        List<Wine> allWines = wineService.getAll();

        assertTrue(!allWines.isEmpty(), "Wines found");
        System.out.println(allWines);
    }


}

