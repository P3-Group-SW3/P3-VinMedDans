// src/main/java/com/vmd/vmdwebshop/repository/OrderRepository.java
package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
// Retrieves an order with a specific sessionID
@Query(value="SELECT * FROM orders WHERE sessionID = ?1", nativeQuery = true)
Orders findBySessionID(String sessionID);
}