package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}