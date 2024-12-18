package com.vmd.vmdwebshop.exception.order;

public class OrdersNotFound extends RuntimeException {

    // Constructor
    public OrdersNotFound() {}

    @Override
    public String getMessage(){
      return "Orders not found";
    }
}
