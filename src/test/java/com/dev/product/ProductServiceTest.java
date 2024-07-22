package com.dev.product;

import com.dev.product.entity.ProductEntity;
import com.dev.product.exception.ResourceNotFoundException;
import com.dev.product.repository.ProductRepository;
import com.dev.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    /**
     * Sets up the Mockito annotations before each test method.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for finding all products.
     * Verifies that the service returns the list of products obtained from the repository.
     */
    @Test
    void testFindAllProducts() {
        // Arrange
        List<ProductEntity> products = List.of(
                new ProductEntity(1L, "Product 1", "10.00", "Description 1"),
                new ProductEntity(2L, "Product 2", "20.00", "Description 2")
        );
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductEntity> result = productService.findAllProducts();

        // Assert
        assertEquals(products, result);
        verify(productRepository, times(1)).findAll();
    }

    /**
     * Test case for finding a product by ID when the ID exists.
     * Verifies that the service returns the product with the specified ID.
     */
    @Test
    void testFindById_ExistingId() {
        // Arrange
        Long productId = 1L;
        ProductEntity product = new ProductEntity(productId, "Product 1", "10.00", "Description 1");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Optional<ProductEntity> result = productService.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(productId);
    }

    /**
     * Test case for finding a product by ID when the ID does not exist.
     * Verifies that the service returns an empty Optional.
     */
    @Test
    void testFindById_NonExistingId() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        Optional<ProductEntity> result = productService.findById(productId);

        // Assert
        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(productId);
    }

    /**
     * Test case for saving a new product.
     * Verifies that the service saves the product using the repository and returns the saved product.
     */
    @Test
    void testSaveProduct() {
        // Arrange
        ProductEntity product = new ProductEntity(null, "New Product", "30.00", "New Description");
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        // Act
        ProductEntity savedProduct = productService.saveProduct(product);

        // Assert
        assertEquals(product, savedProduct);
        verify(productRepository, times(1)).save(product);
    }

    /**
     * Test case for updating an existing product.
     * Verifies that the service updates the product using the repository and returns the updated product.
     */
    @Test
    void testUpdateProduct_ExistingId() {
        // Arrange
        Long productId = 1L;
        ProductEntity existingProduct = new ProductEntity(productId, "Product 1", "10.00", "Description 1");
        ProductEntity updatedProduct = new ProductEntity(productId, "Updated Product", "20.00", "Updated Description");
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updatedProduct);

        // Act
        ProductEntity result = productService.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals(updatedProduct, result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    /**
     * Test case for updating a non-existing product.
     * Verifies that the service throws a ResourceNotFoundException when trying to update a non-existing product.
     */
    @Test
    void testUpdateProduct_NonExistingId() {
        // Arrange
        Long productId = 1L;
        ProductEntity updatedProduct = new ProductEntity(productId, "Updated Product", "20.00", "Updated Description");
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, updatedProduct));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    /**
     * Test case for deleting an existing product.
     * Verifies that the service deletes the product using the repository.
     */
    @Test
    void testDeleteProduct_ExistingId() {
        // Arrange
        Long productId = 1L;
        ProductEntity product = new ProductEntity(productId, "Product 1", "10.00", "Description 1");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    /**
     * Test case for deleting a non-existing product.
     * Verifies that the service throws a ResourceNotFoundException when trying to delete a non-existing product.
     */
    @Test
    void testDeleteProduct_NonExistingId() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).deleteById(anyLong());
    }
}