package com.dev.product;

import com.dev.product.controller.ProductController;
import com.dev.product.entity.ProductEntity;
import com.dev.product.exception.ResourceNotFoundException;
import com.dev.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for finding all products.
     * Verifies that the controller returns the list of products with HTTP status OK.
     */
    @Test
    void testFindAllProducts() {
        // Arrange
        List<ProductEntity> products = List.of(
                new ProductEntity(1L, "Product 1", "10.00", "Description 1"),
                new ProductEntity(2L, "Product 2", "20.00", "Description 2")
        );
        when(productService.findAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductEntity>> response = productController.findAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).findAllProducts();
    }

    /**
     * Test case for finding a product by ID when the ID exists.
     * Verifies that the controller returns the product with HTTP status FOUND.
     */
    @Test
    void testFindProductById_ExistingId() {
        // Arrange
        Long productId = 1L;
        ProductEntity product = new ProductEntity(productId, "Product 1", "10.00", "Description 1");
        when(productService.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<ProductEntity> response = productController.findProductById(productId);

        // Assert
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).findById(productId);
    }

    /**
     * Test case for finding a product by ID when the ID does not exist.
     * Verifies that the controller returns HTTP status NOT_FOUND.
     */
    @Test
    void testFindProductById_NonExistingId() {
        // Arrange
        Long productId = 1L;
        when(productService.findById(productId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProductEntity> response = productController.findProductById(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).findById(productId);
    }

    /**
     * Test case for saving a new product.
     * Verifies that the controller returns the saved product.
     */
    @Test
    void testSaveProduct() {
        // Arrange
        ProductEntity product = new ProductEntity(null, "New Product", "30.00", "New Description");
        when(productService.saveProduct(any(ProductEntity.class))).thenReturn(product);

        // Act
        ProductEntity savedProduct = productController.saveProduct(product);

        // Assert
        assertEquals(product, savedProduct);
        verify(productService, times(1)).saveProduct(product);
    }

    /**
     * Test case for updating a product when the ID exists.
     * Verifies that the controller returns the updated product with HTTP status OK.
     */
    @Test
    void testUpdateProduct_ExistingId() {
        // Arrange
        Long productId = 1L;
        ProductEntity updatedProduct = new ProductEntity(productId, "Updated Product", "20.00", "Updated Description");
        when(productService.updateProduct(productId, updatedProduct)).thenReturn(updatedProduct);

        // Act
        ResponseEntity<ProductEntity> response = productController.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
        verify(productService, times(1)).updateProduct(productId, updatedProduct);
    }

    /**
     * Test case for updating a product when the ID does not exist.
     * Verifies that the controller throws ResourceNotFoundException.
     */
    @Test
    void testUpdateProduct_NonExistingId() {
        // Arrange
        Long productId = 1L;
        ProductEntity updatedProduct = new ProductEntity(productId, "Updated Product", "20.00", "Updated Description");
        when(productService.updateProduct(productId, updatedProduct)).thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productController.updateProduct(productId, updatedProduct));
        verify(productService, times(1)).updateProduct(productId, updatedProduct);
    }

    /**
     * Test case for deleting a product when the ID exists.
     * Verifies that the controller returns HTTP status NO_CONTENT.
     */
    @Test
    void testDeleteProduct_ExistingId() {
        // Arrange
        Long productId = 1L;
        doNothing().when(productService).deleteProduct(productId);

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(productId);
    }

    /**
     * Test case for deleting a product when the ID does not exist.
     * Verifies that the controller throws ResourceNotFoundException.
     */
    @Test
    void testDeleteProduct_NonExistingId() {
        // Arrange
        Long productId = 1L;
        doThrow(ResourceNotFoundException.class).when(productService).deleteProduct(productId);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productController.deleteProduct(productId));
        verify(productService, times(1)).deleteProduct(productId);
    }
}