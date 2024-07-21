package com.dev.product.service.impl;

import com.dev.product.entity.ProductEntity;
import com.dev.product.exception.ResourceNotFoundException;
import com.dev.product.repository.ProductRepository;
import com.dev.product.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

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

    @Override
    public void deleteProduct(Long id) {
        Optional<ProductEntity> product = this.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }
}
