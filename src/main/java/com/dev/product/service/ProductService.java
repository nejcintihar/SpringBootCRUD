package com.dev.product.service;

import com.dev.product.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing products.
 * Defines methods for performing CRUD operations on products.
 */
public interface ProductService {
    /**
     * Retrieves all products.
     *
     * @return a list of all ProductEntity objects
     */
    List<ProductEntity> findAllProducts();

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return an Optional containing the ProductEntity if found, or an empty Optional otherwise
     */
    Optional<ProductEntity> findById(Long id);

    /**
     * Saves a new product.
     *
     * @param productEntity the ProductEntity object to save
     * @return the saved ProductEntity object
     */
    ProductEntity saveProduct(ProductEntity productEntity);

    /**
     * Updates an existing product.
     *
     * @param id the ID of the product to update
     * @param productEntity the updated ProductEntity object
     * @return the updated ProductEntity object
     */
    ProductEntity updateProduct(Long id, ProductEntity productEntity);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    void deleteProduct(Long id);
}