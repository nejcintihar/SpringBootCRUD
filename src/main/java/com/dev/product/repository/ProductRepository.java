package com.dev.product.repository;

import com.dev.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing database operations on ProductEntity.
 * Extends the JpaRepository interface, which provides basic CRUD functionality.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    /**
     * Finds products by their name.
     *
     * @param name the name of the product to search for
     * @return a list of ProductEntity objects matching the given name
     */
    List<ProductEntity> findByName(String name);
}