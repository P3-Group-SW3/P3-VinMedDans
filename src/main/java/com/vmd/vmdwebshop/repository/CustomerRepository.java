package com.vmd.vmdwebshop.repository;

import com.vmd.vmdwebshop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
