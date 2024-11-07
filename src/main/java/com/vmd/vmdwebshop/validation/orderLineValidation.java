package com.vmd.vmdwebshop.validation;

import com.vmd.vmdwebshop.model.OrderLine;

public class orderLineValidation implements validationInterface{

    @Override
    public boolean verifyID() {
        return false;
    }

    @Override
    public boolean verifyAmount() {
        return false;
    }
}
