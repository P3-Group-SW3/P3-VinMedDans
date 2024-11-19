package com.vmd.vmdwebshop.Interface;

import java.util.List;

public interface WineAdministrativeMethodsInterface<T> {

    List<T> getAll();
    List<T> createAndEdit(T typeParameter, Long ID);
    List<T> delete(Long ID);
}
