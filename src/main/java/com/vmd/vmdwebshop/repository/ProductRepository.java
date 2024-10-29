package com.vmd.vmdwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vmd.vmdwebshop.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
