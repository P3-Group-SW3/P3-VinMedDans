package com.vmd.vmdwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vmd.vmdwebshop.model.*;
import java.util.*;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {
}
