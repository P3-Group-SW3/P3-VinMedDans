// src/main/java/com/vmd/vmdwebshop/exception/order/OrderNotFoundInDatbase.java
package com.vmd.vmdwebshop.exception.order;

public class OrderNotFoundInDatbase extends RuntimeException {
    private Long id;
    private String sessionId;

    public OrderNotFoundInDatbase(Long id) {
        this.id = id;
    }

    public OrderNotFoundInDatbase(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getMessage() {
        if (id != null) {
            return "The order with the id: " + id + " was not found";
        } else {
            return "The order with the session id: " + sessionId + " was not found";
        }
    }
}