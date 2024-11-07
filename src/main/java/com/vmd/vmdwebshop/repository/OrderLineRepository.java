package com.vmd.vmdwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vmd.vmdwebshop.model.*;
import java.util.*;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    @Query(value="SELECT * FROM orderline WHERE customerID = ?1", nativeQuery = true)
    List<OrderLine> findAllByCustomerId(Long customerID);

    @Modifying
    @Query(value="DELETE FROM orderline WHERE customerID = ?1", nativeQuery = true)
    void deleteOrderLinesByCustomerId(Long customerID);

    OrderLine findByCustomerIDAndWineID(Long customerID, Long wineID);

    void deleteOrderLineByCustomerIDAndWineID(Long customerID, Long wineID);
}