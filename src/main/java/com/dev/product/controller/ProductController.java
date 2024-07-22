package com.dev.product.controller;

import com.dev.product.entity.ProductEntity;
import com.dev.product.exception.ResourceNotFoundException;
import com.dev.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing product resources.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructs a new ProductController with the specified ProductService.
     *
     * @param productService the ProductService to use for managing products
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products.
     *
     * @return a ResponseEntity containing the list of products and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<ProductEntity>> findAllProducts() {
        final List<ProductEntity> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity containing the product and an HTTP status code
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> findProductById(@PathVariable("id") Long id) {
        Optional<ProductEntity> product = productService.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product.get(), HttpStatus.FOUND);
    }

    /**
     * Saves a new product.
     *
     * @param productEntity the product to save
     * @return the saved product
     */
    @PostMapping
    public ProductEntity saveProduct(@Valid @RequestBody ProductEntity productEntity) {
        return productService.saveProduct(productEntity);
    }

    /**
     * Updates an existing product.
     *
     * @param id      the ID of the product to update
     * @param product the updated product data
     * @return a ResponseEntity containing the updated product and an HTTP status code
     * @throws ResourceNotFoundException if the product with the specified ID is not found
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity product) {
        try {
            ProductEntity updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity with no content and an HTTP status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}