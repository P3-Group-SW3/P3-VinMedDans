package com.vmd.vmdwebshop.Interface;

import java.util.List;

//this interface is used for the WineService, EventService and DistributorService
//as these three services mostly has the same methods
public interface AdministrativeMethodsInterface<T> {

    //using T as a type parameter
    List<T> getAll();
    List<T> createAndEdit(T typeParameter, Long ID);
    List<T> delete(Long ID);
}
