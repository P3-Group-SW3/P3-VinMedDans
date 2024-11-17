package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {
    @Query(value="SELECT * FROM wine WHERE wine.ID = ?1", nativeQuery = true)
    Wine findByWineID(Long ID);
}
