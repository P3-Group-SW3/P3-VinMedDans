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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    List<Wine> wineList = new ArrayList<>() {
    };

    Wine wine = null;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test

        wine = new Wine("Vin", "URL", 189.0, 123, "Rødvin");
    }

    @Test
    public void TestCreateAndEditWine01() {
        when(wineRepository.findById(Long.parseLong("99"))).thenReturn(Optional.empty());

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, () -> { wineService.createAndEdit(wine, Long.parseLong("99")); });
        assertEquals("The Wine was not updated", newException.getMessage());
    }

    @Test
    public void TestDeleteWine(){
        when(wineRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());

        WineNotFoundException newException = assertThrows(WineNotFoundException.class, ()->{ wineService.delete(Long.parseLong("1")); });
        assertEquals("Wine does not exist in the database", newException.getMessage());
    }
}

