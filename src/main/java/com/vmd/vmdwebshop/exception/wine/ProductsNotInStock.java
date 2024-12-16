package com.vmd.vmdwebshop.exception.wine;

public class ProductsNotInStock extends RuntimeException {
    public ProductsNotInStock(String message) {
        super(message);
    } //could be left out
}
