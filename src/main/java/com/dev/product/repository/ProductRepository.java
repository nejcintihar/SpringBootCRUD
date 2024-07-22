package com.dev.product.repository;

import com.dev.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on ProductEntity.
 * Extends the JpaRepository interface, which provides basic CRUD functionality.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}