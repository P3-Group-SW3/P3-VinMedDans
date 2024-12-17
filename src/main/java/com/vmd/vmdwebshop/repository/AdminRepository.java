// src/main/java/com/vmd/vmdwebshop/repository/AdminRepository.java
package com.vmd.vmdwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vmd.vmdwebshop.model.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);

}