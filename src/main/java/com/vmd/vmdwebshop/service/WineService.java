// src/main/java/com/vmd/vmdwebshop/service/WineService.java
package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.Interface.AdministrativeMethods;
import com.vmd.vmdwebshop.exception.wine.*;
import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Wine> createAndEdit(Wine wine, Long ID) {

            if (ID == null) {
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

        wineRepository.deleteById(ID);

        return wineRepository.findAll();
    }
}