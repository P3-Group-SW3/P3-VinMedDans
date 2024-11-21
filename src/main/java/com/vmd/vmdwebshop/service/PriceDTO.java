package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.OrderLine;

import java.util.ArrayList;
import java.util.List;

public class PriceDTO {
    private Double totalPrice;

    public PriceDTO(){
        totalPrice = 0.0;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void addToTotalPrice(Double totalPrice) {
        this.totalPrice += totalPrice;
    }


