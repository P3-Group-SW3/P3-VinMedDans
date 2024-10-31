package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    @Query(value="SELECT * FROM OrderLine WHERE customer_ID = ?1", nativeQuery = true)
    List<OrderLine> findByCustomerId(Long customerId);

    OrderLine findByCustomerIdAndWineId(Long customer_id, Long wine_id);

    @Modifying
    @Query(value="DELETE * FROM OrderLine WHERE customer_ID = ?1", nativeQuery = true)
    void deleteOrderLinesByCustomerId(Long customerId);
}
