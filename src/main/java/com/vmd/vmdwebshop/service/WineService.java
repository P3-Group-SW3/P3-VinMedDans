// src/main/java/com/vmd/vmdwebshop/service/WineService.java
package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethods;
import com.vmd.vmdwebshop.exception.wine.*;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WineService implements AdministrativeMethods<Wine> {

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

            if (ID == 0) {
                wineRepository.save(wine);
            } else {
                Wine existingWine = wineRepository.findById(ID).orElse(null);

                if(existingWine == null){
                    throw new WineNotFoundException("The Wine was not updated");
                }

                existingWine.setAmountLeft(wine.getAmountLeft());
                wineRepository.save(existingWine);
            }

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

        } catch (DataIntegrityViolationException e) {
            throw new WineNotDeletedException(e.getMessage());
        }


        return wineRepository.findAll();
    }

    public List<Wine> changeActiveState(String ID) {
        Wine existingWine = null;

        try {
            existingWine = wineRepository.findById(Long.parseLong(ID)).orElse(null);
        } catch (DataAccessException e) {
            //throw new WineDataAccessException("The wine was not retrieved from the database");
        }

        try {
            if (existingWine != null){
                existingWine.changeActiveState();
            }
        } catch (DataAccessException e){
            //throw new WineDataAccessException("The Active State of the wine was not updated");
        }

        return wineRepository.findAll();
    }
}