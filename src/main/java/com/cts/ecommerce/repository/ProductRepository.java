package com.cts.ecommerce.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
