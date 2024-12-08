package com.vmd.vmdwebshop.exception.order;

public class StockNotUpdatedFromOrder extends RuntimeException {
    public StockNotUpdatedFromOrder(String message) {
        super(message);
    }
}
