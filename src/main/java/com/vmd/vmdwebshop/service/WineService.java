// src/main/java/com/vmd/vmdwebshop/service/WineService.java
package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.Wine;
import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WineService {

    @Autowired
    private WineRepository wineRepository;

    public List<Wine> getAllWines() {
        return wineRepository.findAll();
    }

    public Wine getWineById(Long id) {
        return wineRepository.findById(id).orElse(null);
    }

    public boolean createWine(Wine wine) {
        wineRepository.save(wine);
        return true;
    }
}