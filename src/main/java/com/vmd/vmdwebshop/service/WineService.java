// src/main/java/com/vmd/vmdwebshop/service/WineService.java
package com.vmd.vmdwebshop.service;
import com.vmd.vmdwebshop.Interface.AdministrativeMethodsInterface;
import com.vmd.vmdwebshop.exception.wine.*;
import com.vmd.vmdwebshop.model.OrderLine;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
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

    @Override
    public List<Wine> getAll() {
        List<Wine> wineList = wineRepository.findAll();

        if (wineList.isEmpty()){
            throw new WineNotFoundException("No wines were found in the database");
        }

        return wineList;
    }

    public Wine getWineById(Long wineID) {
        Wine existingWine = wineRepository.findById(wineID).orElse(null);

        if (existingWine == null){
            throw new WineNotFoundException("The wine was not found");
        }

        return existingWine;
    }

    @Override
    public List<Wine> createAndEdit(Wine wine, Long ID) {

        Wine existingWine;

        try {
            Optional<Wine> optionalWine = wineRepository.findById(ID);
            existingWine = optionalWine.orElse(null);
        }catch (DataAccessException e) {
            throw new WineDataAccessException("Failed to retrieve the wine from the database");}

        try {

            if (existingWine != null) {
                existingWine.setAmountLeft(wine.getAmountLeft());
                existingWine.setDescription(wine.getDescription());
                existingWine.setImageURL(wine.getImageURL());
                existingWine.setPrice(wine.getPrice());
                existingWine.setName(wine.getName());
                wineRepository.save(existingWine);
            } else {
                wineRepository.save(wine);
            }
        } catch (DataIntegrityViolationException e) {
            throw new WineNotUpdatedException("Failed to update wine");
        } catch (DataAccessException e)
        { throw new WineDataAccessException("Failed to save wine to the database");}

        return wineRepository.findAll();
    }


    /** THis method deletes a wine entity in the database, based on the ID
     * Throws an exception if the wine isn't found, or if the object isn't properly deleted.
     * @param ID
     * @return List<Wine>
     */
    @Override
    public List<Wine> delete(Long ID) {
        Wine existingWine = wineRepository.findById(ID).orElse(null);

        if (existingWine == null){
            throw new WineNotFoundException("Wine does not exist in the database");
        }

        try{
            wineRepository.deleteById(ID);

        } catch (DataAccessException e) {
            throw new WineNotDeletedException(e.getMessage());
        }

        return wineRepository.findAll();
    }

    /** This method first tries to find the existing wine.
     * If not found, it will throw an exception
     * if found it will change the activeState attribute.
     * Return a list of all wines.
     * @param ID
     * @return List<Wine>
     */
    public List<Wine> changeActiveState(String ID) {
        Wine existingWine = null;

        try {
            existingWine = wineRepository.findById(Long.parseLong(ID)).orElse(null);
        } catch (DataAccessException e) {
            throw new WineDataAccessException("The wine was not retrieved from the database");
        }

        try {
            if (existingWine != null){
                existingWine.changeActiveState();
            }
        } catch (DataAccessException e){
            throw new WineDataAccessException("The Active State of the wine was not updated");
        }

        return wineRepository.findAll();
    }

    public void updateStockFromOrder(List<OrderLine> orderLineList){
        try{
            for (OrderLine orderLine : orderLineList){
                Wine wine = wineRepository.getById(orderLine.getWineID());

                wine.setAmountLeft(wine.getAmountLeft() - orderLine.getAmount());

                wineRepository.save(wine);
            }

        } catch (DataIntegrityViolationException e) {
            throw new WineNotUpdatedException("The stock of the wine was not updated");

        } catch (DataAccessException e){
            throw new WineDataAccessException("The wine was not retrieved from the database");
        }
    }
}