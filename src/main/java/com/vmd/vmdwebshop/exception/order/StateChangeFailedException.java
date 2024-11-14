package com.vmd.vmdwebshop.exception.order;

import com.vmd.vmdwebshop.model.Orders;

public class StateChangeFailedException extends RuntimeException {

    private Orders.State state1, state2;
    public StateChangeFailedException(Orders.State state1, Orders.State state2) {
        this.state1 = state1;
        this.state2 = state2;
    }

    @Override
    public String getMessage(){
        return "The order failed to change from " + state1 + " to " + state2;
    }
}
