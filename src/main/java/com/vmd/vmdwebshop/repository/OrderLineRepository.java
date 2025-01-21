package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.OrderLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    //@Query lets us define custom SQL queries
    //Retrieves all orderlines with a specific customerID
    @Query(value="SELECT * FROM orderline WHERE customerID = ?1", nativeQuery = true)
    List<OrderLine> findAllByCustomerId(String customerID);

    //Deletes orderlines with a specific customerID
    //@Modifying is added, because we modify the database
    @Modifying
    @Query(value="DELETE FROM orderline WHERE customerID = ?1", nativeQuery = true)
    void deleteOrderLinesByCustomerId(String customerID);

    //Finds a specific orderline by customer and wine ID
    @Query(value="SELECT * FROM orderline WHERE orderline.customerID = ?1 AND orderline.wineID = ?2", nativeQuery = true)
    OrderLine findByCustomerIDAndWineID(String customerID, Long wineID);

    void deleteOrderLineByCustomerIDAndWineID(String customerID, Long wineID);
}