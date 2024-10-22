package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.SampleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleUserRepository extends JpaRepository<SampleUser, Long> {
    // Custom query methods can be defined here
}
