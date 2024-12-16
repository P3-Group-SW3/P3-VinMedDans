// src/main/java/com/vmd/vmdwebshop/service/WineService.java
package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethodsInterface;
import com.vmd.vmdwebshop.exception.wine.*;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class WineService implements AdministrativeMethodsInterface<Wine> {

    @Autowired
    private WineRepository wineRepository;

    /**
     * Method to retrieve a list of all wines
     * @return a list of wines List<Wine>
     * @throws WineNotFoundException if no wines are found
     */
    @Override
    public List<Wine> getAll() {
        List<Wine> wineList = wineRepository.findAll();

        if (wineList.isEmpty()) {
            throw new WineNotFoundException("No wines were found in the database");
        }

        return wineList;
    }

    /**
     * Find a specific wine by its ID
     * @param wineID
     * @return the specific wine object
     * @throws WineNotFoundException if no wines are found
     */
    public Wine getWineById(Long wineID) {
        Wine existingWine = wineRepository.findById(wineID).orElse(null);

        if (existingWine == null) {
            throw new WineNotFoundException("The wine was not found");
        }

        return existingWine;
    }

    /**
     * Method to save a new wine entity in the database, or update an existing
     * The ID is passed, as it is received separately as a path variable
     * @param wine
     * @param ID
     * @return a list of wines
     * @throws WineDataAccessException if errors occurs in the database when retrieving wines,
     * @throws WineNotUpdatedException if the wine is not updated
     * @throws WineDataAccessException if the wine is not updated
     */
    @Override
    public List<Wine> createAndEdit(Wine wine, Long ID) {
        Wine existingWine;

        try {
            // optional is required, for if no wine is found, which can happen often
            Optional<Wine> optionalWine = wineRepository.findById(ID);
            existingWine = optionalWine.orElse(null); // if no wine is found the existingWine variable is set to null
        } catch (DataAccessException e) {
            throw new WineDataAccessException("Failed to retrieve the wine from the database");
        }

        try {
            if (existingWine != null) {
                BeanUtils.copyProperties(wine, existingWine, "ID"); // copies the properties of the wine variable onto the existing wine object

                wineRepository.save(existingWine);
            } else {
                wineRepository.save(wine);
            }
        } catch (DataIntegrityViolationException e) { // if errors happen when trying to update the object
            throw new WineNotUpdatedException("Failed to update wine");
        } catch (DataAccessException e) {
            throw new WineDataAccessException("Failed to save wine to the database");
        }

        return wineRepository.findAll(); // finds all wines, and returns to client
    }


    /** This method deletes a wine entity in the database, based on the ID
     * @param ID
     * @return List<Wine>
     * @throws WineNotFoundException if the wine is not found
     * @throws WineNotDeletedException if the object isn't properly deleted
     */
    @Override
    public List<Wine> delete(Long ID) {
        Wine existingWine = wineRepository.findById(ID).orElse(null);
        // first we try to find the object
        // if object doesn't exist we set the existingWine variable to null

        if (existingWine == null) {
            throw new WineNotFoundException("Wine does not exist in the database");
        }

        try {
            wineRepository.deleteById(ID);
        } catch (DataAccessException e) { // if the wine isn't properly deleted in the database
            throw new WineNotDeletedException(e.getMessage());
        }

        return wineRepository.findAll(); // returns alle other wines to client
    }

    /** This method first tries to find the existing wine.
     * If not found, it will throw an exception
     * if found it will change the activeState attribute.
     * Return a list of all wines.
     * @param ID
     * @return List<Wine>
     * @throws WineDataAccessException if an object is not properly retrieved or if the state isn't properly updated
     */
    public List<Wine> changeActiveState(String ID) {
        Wine existingWine = null; // initialising the object

        try {
            existingWine = wineRepository.findById(Long.parseLong(ID)).orElse(null);
        } catch (DataAccessException e) {
            throw new WineDataAccessException("The wine was not retrieved from the database");
        }

        try {
            if (existingWine != null) {
                existingWine.changeActiveState();
                // changeActiveState changes a boolean value in the Wine object
            }
        } catch (DataAccessException e) {
            throw new WineDataAccessException("The Active State of the wine was not updated");
        }

        return wineRepository.findAll(); // finds all wines to return to sender
    }

    /**
     * Method to update the stock of each wine in an order.
     * @param orderLineList
     * @throws WineNotUpdatedException if the objects aren't properly updated
     * @throws WineDataAccessException if an object isn't retrieved
     */
    public void updateStockFromOrder(List<OrderLine> orderLineList) {
        try {
            //for each orderline, the wine object is found, the stock is updated
            // and the object is saved and updated in the database
            for (OrderLine orderLine : orderLineList) {
                Wine wine = wineRepository.getById(orderLine.getWineID());

                wine.setStock(wine.getStock() - orderLine.getAmount()); // updated the stock

                wineRepository.save(wine);
            }
        } catch (DataIntegrityViolationException e) {
            throw new WineNotUpdatedException("The stock of the wine was not updated");
        } catch (DataAccessException e) {
            throw new WineDataAccessException("The wine was not retrieved from the database");
        }
    }
}
