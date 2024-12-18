package com.vmd.vmdwebshop.exception.order;

public class OrderNotFoundInDatbase extends RuntimeException {

    private Long id;

    // Constructor
    public OrderNotFoundInDatbase(Long id) {
        this.id = id;
    }

    //builds a more precise error message,
    //uses the id that is used to look in the database
    @Override
    public String getMessage(){
        return "The order with the id: " + id +" was not found";
    }
}
