package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM event WHERE ID = ?1", nativeQuery = true)
    Event findById(Long ID);
}