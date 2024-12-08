// src/main/java/com/vmd/vmdwebshop/repository/OrderRepository.java
package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findOrderBySessionID(String sessionID);
}