// src/main/java/com/vmd/vmdwebshop/service/WineService.java
package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethods;
import com.vmd.vmdwebshop.exception.wine.*;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        return wineRepository.findAll();
    }

    public Wine getWineById(Long wineID) {
        return wineRepository.findById(wineID).orElse(null);
    }

    @Override
    public List<Wine> createAndEdit(Wine wine) {

        try {
            if (wine.getID() == null) {
                wineRepository.save(wine);
            } else {
                Wine existingWine = wineRepository.findById(wine.getID()).orElse(null);
                existingWine.setAmountLeft(wine.getAmountLeft());
                wineRepository.save(existingWine);
            }
        } catch (DataAccessException e) {
            throw new WineDidNotUpdateDataBaseException("The wine object has not been saved or updated in the database");
        }

        return wineRepository.findAll();
    }

    @Override
    public List<Wine> delete(Wine wine) {
        Wine existingWine = wineRepository.findById(wine.getID()).orElse(null);

        if (existingWine == null){
            throw new NullPointerException("Wine does not exist in the database");
        }

        wineRepository.deleteById(wine.getID());

        return wineRepository.findAll();
    }
}