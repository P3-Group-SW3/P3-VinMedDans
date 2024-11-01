package com.vmd.vmdwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vmd.vmdwebshop.model.*;
import java.util.*;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    @Query(value="SELECT * FROM OrderLine WHERE customer_ID = ?1", nativeQuery = true)
    List<OrderLine> findByCustomerId(Long customer_ID);

    @Modifying
    @Query(value="DELETE FROM OrderLine WHERE customer_ID = ?1", nativeQuery = true)
    void deleteOrderLinesByCustomerId(Long customer_ID);

    OrderLine findByCustomerIDAndWineID(Long customerId, Long wineId);
}