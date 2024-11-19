package com.vmd.vmdwebshop.service;

import com.vmd.vmdwebshop.model.OrderLine;

import java.util.ArrayList;
import java.util.List;

public class PriceDTO {
    private Double totalPrice;
    private List<OrderLine> orderLines;

    public PriceDTO(List<OrderLine> orderLines){
        totalPrice = 0.0;
        this.orderLines = orderLines;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void addToTotalPrice(Double totalPrice) {
        this.totalPrice += totalPrice;
    }



    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
