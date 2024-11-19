package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM event WHERE event.ID = ?1", nativeQuery = true)
    Event findByEventID(Long ID);
}