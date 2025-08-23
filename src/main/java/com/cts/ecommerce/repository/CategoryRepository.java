package com.cts.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
