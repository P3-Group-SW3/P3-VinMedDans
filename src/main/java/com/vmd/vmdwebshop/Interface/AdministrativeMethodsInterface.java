package com.vmd.vmdwebshop.Interface;

import java.util.List;

public interface AdministrativeMethodsInterface<T> {
    //The type parameter T helps to ensure Type safety.
    //Factory Method Design Pattern
    List<T> getAll();
    List<T> createAndEdit(T typeParameter, Long ID);
    List<T> delete(Long ID);
}
