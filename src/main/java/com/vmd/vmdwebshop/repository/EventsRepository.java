package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
}
