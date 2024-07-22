package com.dev.product.service.impl;

import com.dev.product.entity.ProductEntity;
import com.dev.product.exception.ResourceNotFoundException;
import com.dev.product.repository.ProductRepository;
import com.dev.product.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ProductService interface.
 * Provides the business logic for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructs a new ProductServiceImpl with the specified ProductRepository.
     *
     * @param productRepository the ProductRepository to be used
     */
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all ProductEntity objects
     */
    @Override
    public List<ProductEntity> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return an Optional containing the ProductEntity if found, or an empty Optional otherwise
     */
    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Saves a new product.
     *
     * @param productEntity the ProductEntity object to save
     * @return the saved ProductEntity object
     */
    @Override
    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    /**
     * Updates an existing product.
     *
     * @param id the ID of the product to update
     * @param productEntity the updated ProductEntity object
     * @return the updated ProductEntity object
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    public ProductEntity updateProduct(Long id, ProductEntity productEntity) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        ProductEntity _product = product.get();
        _product.merge(productEntity);

        return productRepository.save(_product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    public void deleteProduct(Long id) {
        Optional<ProductEntity> product = this.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }
}