package com.vmd.vmdwebshop.service;

public class StringValidation {

    public boolean stringValidation(String string){
        return string.matches("[a-zA-Z0-9]+$");
    }
}
