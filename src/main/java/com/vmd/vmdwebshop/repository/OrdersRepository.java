// src/main/java/com/vmd/vmdwebshop/repository/OrdersRepository.java
package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}