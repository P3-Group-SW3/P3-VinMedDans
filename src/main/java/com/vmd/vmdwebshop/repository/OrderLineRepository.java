package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByCustomerId(Long customer_id);
    OrderLine findByCustomerIdAndWineId(Long customer_id, Long wine_id);

}
