// src/main/java/com/vmd/vmdwebshop/service/WineService.java
package com.vmd.vmdwebshop.service;
import com.vmd.vmdwebshop.Interface.AdministrativeMethodsInterface;
import com.vmd.vmdwebshop.exception.wine.*;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            existingWine = wineRepository.findByWineID(ID);
        }catch (DataAccessException e) {
            throw new WineDataAccessException("Failed to retrieve the wine from the database");}

        try {

            if (existingWine != null) {
                existingWine.setAmountLeft(wine.getAmountLeft());
                existingWine.setDescription(wine.getDescription());
                existingWine.setImageURL(wine.getImageURL());
                existingWine.setPrice(wine.getPrice());
                existingWine.setName(wine.getName());
                wineRepository.save(wine);
            } else {
                wineRepository.save(wine);
            }
        } catch (DataIntegrityViolationException e) {
            throw new WineNotUpdatedException("Failed to update wine");
        } catch (DataAccessException e)
        { throw new WineDataAccessException("Failed to save wine to the database");}

        return wineRepository.findAll();
    }

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
}