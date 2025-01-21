package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Distributor;
import com.vmd.vmdwebshop.repository.DistributorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessResourceFailureException;
import com.vmd.vmdwebshop.exception.distributor.*;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class TestDistributorService {

    @Mock
    private DistributorRepository distributorRepository;

    @InjectMocks
    private  DistributorService distributorService;

    List<Distributor> distributorList = new ArrayList<>() {};

    Distributor distributor1;
    Distributor distributor2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        distributor1 = new Distributor("name", "location", "websiteURL");

        distributorList.clear();
        distributorList.add(distributor1);
    }

    /** Test that getAll method returns a list of all distributors in the database */
    @Test
    public void TestGetAll01() {
        when(distributorRepository.findAll())
                .thenReturn(distributorList);

        List<Distributor> distributors = distributorService.getAll();

        assertNotNull(distributors);
    }

    /** Test that getAll method returns an empty list if no distributors currently exist */
    @Test
    public void TestGetAll02() {
        when(distributorRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Distributor> distributors = distributorService.getAll();

        assertTrue(distributors.isEmpty());
    }

    /** Test that getAll method throws an exception if there is a failure when accessing the database */
    @Test
    public void TestGetAll03() {
        when(distributorRepository.findAll())
                .thenThrow(DataAccessResourceFailureException.class);

        assertThrows(DistributorDataAccessException.class, () -> distributorService.getAll());
    }

    /** Test that when the createAndEdit method is called, the service returns a list of distributors*/
    @Test
    public void TestCreateAndEdit01() {
        when(distributorRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(distributor1));

        List<Distributor> distributors = distributorService.createAndEdit(distributor1, Long.parseLong("1"));

        assertNotNull(distributors);
    }

    /** Test that createAndEdit method throws an exception if there is a failure when accessing the database */
    @Test
    public void TestCreateAndEdit02() {
        when(distributorRepository.findById(Long.parseLong("1")))
                .thenThrow(DataAccessResourceFailureException.class);

        assertThrows(DistributorDataAccessException.class, () -> distributorService.createAndEdit(distributor1,Long.parseLong("1")));
    }

    /** Test that createAndEdit method throws an exception if the method failed to update the distributor */
    @Test
    public void TestCreateAndEdit03() {
        when(distributorRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(distributor1));

        doThrow(DataIntegrityViolationException.class)
                .when(distributorRepository).save(distributor1);

        assertThrows(DistributorNotUpdatedException.class, () -> distributorService.createAndEdit(distributor1, Long.parseLong("1")));
    }

    /** Test that createAndEdit method throws an exception if the method fails to save the distributor */
    @Test
    public void TestCreateAndEdit04() {
        when(distributorRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(distributor1));

        doThrow(DataAccessResourceFailureException.class)
                .when(distributorRepository).save(distributor1);

        assertThrows(DistributorDataAccessException.class, () -> distributorService.createAndEdit(distributor1, Long.parseLong("1")));
    }

    /** Test that delete method returns a list of all remaining distributors after deletion */
    @Test
    public void TestDelete01() {
        when(distributorRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(distributor1));

        List<Distributor> distributors = distributorService.delete(Long.parseLong("1"));

        assertNotNull(distributors);
    }

    /** Test whether an exception is thrown when a specific distributor does not exist */
    @Test
    public void TestDelete02 () {
        when(distributorRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> distributorService.delete(Long.parseLong("1")));
    }
}
