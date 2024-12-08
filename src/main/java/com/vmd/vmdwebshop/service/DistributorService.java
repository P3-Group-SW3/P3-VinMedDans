package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethodsInterface;
import com.vmd.vmdwebshop.exception.event.EventNotFoundException;
import com.vmd.vmdwebshop.model.Distributor;
import com.vmd.vmdwebshop.exception.distributor.*;
import com.vmd.vmdwebshop.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DistributorService implements AdministrativeMethodsInterface<Distributor> {

    private final DistributorRepository distributorRepository;

    @Autowired
    public DistributorService(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }

    /**
     * getAll
     * This method finds and returns all existing distributors in the database.
     * If no distributors are found, an exception will be thrown.
     * @return List<Distributor> returns a list of all distributors in the database.
     * @throws DistributorNotFoundException if a distributor cannot be retrieved from the database.
     */
    @Override
    public List<Distributor> getAll() {

        List<Distributor> distributorList;

        try {
            distributorList = distributorRepository.findAll();}
        catch (DataAccessException e) {
            throw new DistributorDataAccessException("Failure to retrieve distributors from the database");
        }

        if(distributorList.isEmpty()) {
            System.out.println("There are currently no distributors in the database");
        }

        return distributorList;
    }

    /**
     * createAndEdit
     * This method finds a specific distributor by Id. If the distributor already exists, its attributes will be
     * updated with the new values. If the distributor does not already exist, a new distributor will be created
     * and saved to the database.
     * @param distributor object containing the updated data.
     * @param ID of the distributor.
     * @return List<Distributor> returns a list of all distributors.
     * @throws  DistributorDataAccessException if there is failure when retrieving/ saving distributor data to/from the database.
     * @throws DistributorNotUpdatedException if there is a failure when updating the distributor data.
     */
    @Override
    public List<Distributor> createAndEdit(Distributor distributor, Long ID){

        Distributor existingDistributor;

        try {
            Optional<Distributor> optionalDistributor = distributorRepository.findById(ID);
            existingDistributor = optionalDistributor.orElse(null);}
        catch (DataAccessException e) {
            throw new DistributorDataAccessException("Failed to retrieve the distributor from the database");
        }

        try {
            if (existingDistributor != null) {
                existingDistributor.setName(distributor.getName());
                existingDistributor.setLocation(distributor.getLocation());
                existingDistributor.setWebsiteURL(distributor.getWebsiteURL());
                distributorRepository.save(existingDistributor);
            } else {
                distributorRepository.save(distributor);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DistributorNotUpdatedException("Failed to update distributor");
        } catch (DataAccessException e) {
            throw new DistributorDataAccessException("Failed to save distributor to the database");
        }

        return distributorRepository.findAll();

    }

    /**
     * delete
     * This method finds a distributor by Id. If no distributor is found, an exception is thrown.
     * If a distributor is found, it will attempt to delete it from the database.
     * @param ID
     * @return List<Distributor> list of all remaining distributors.
     * @throws  DistributorNotFoundException if no distributor with the given Id is found.
     * @throws  DistributorNotDeletedException if the distributor could not be deleted from the database.
     */
    @Override
    public List<Distributor> delete(Long ID){

        Optional<Distributor> optionalDistributor = distributorRepository.findById(ID);
        Distributor existingDistributor = optionalDistributor.orElse(null);

        if (existingDistributor == null) {
            throw new NullPointerException("No such distributor exists");
        }
        try {
            distributorRepository.deleteById(ID);
        } catch (DataAccessException e) {
            throw new DistributorNotDeletedException("Failed to delete distributor in the database");
        }

        return distributorRepository.findAll();

    }
}
