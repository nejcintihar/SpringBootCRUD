package com.dev.product.service;

import com.dev.product.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductEntity> findAllProducts();
    Optional<ProductEntity> findById(Long id);
    ProductEntity saveProduct(ProductEntity productEntity);
    ProductEntity updateProduct(Long id, ProductEntity productEntity);
    void deleteProduct(Long id);
}
