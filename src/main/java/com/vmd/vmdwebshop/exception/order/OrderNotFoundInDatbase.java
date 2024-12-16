package com.vmd.vmdwebshop.exception.order;

public class OrderNotFoundInDatbase extends RuntimeException {

    private Long id;

    // Constructor
    public OrderNotFoundInDatbase(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage(){
        return "The order with the id: " + id +" was not found";
    }
}
