package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.repository.WineRepository;
import org.springframework.stereotype.Service;
import com.vmd.vmdwebshop.model.Wine;

import java.util.List;

@Service
public class  WineService {

    private WineRepository wineRepository;

    public List<Wine> getAllWines(){ return wineRepository.findAll(); }

    public Wine createWine(Wine wine){ return wineRepository.save(wine); }
}
