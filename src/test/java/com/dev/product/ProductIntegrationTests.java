package com.dev.product;

import com.dev.product.entity.ProductEntity;
import com.dev.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<ProductEntity> testProducts = new ArrayList<>();

    /**
     * Cleans up the test data after each test method.
     * Finds and deletes test products based on their individual names.
     */
    @AfterEach
    void tearDown() {
        List<ProductEntity> productsToDelete = new ArrayList<>();
        productsToDelete.addAll(productRepository.findByName("Product 1"));
        productsToDelete.addAll(productRepository.findByName("Product 2"));
        productsToDelete.addAll(productRepository.findByName("Updated Product"));
        productsToDelete.addAll(productRepository.findByName("test"));
        productRepository.deleteAll(productsToDelete);
        testProducts.clear();
    }

    /**
     * Test case for creating a new product.
     * Verifies that the API returns the created product with HTTP status OK.
     */
    @Test
    void testCreateProduct() throws Exception {
        // Arrange
        ProductEntity product = new ProductEntity(null, "Product 1", "10.00", "Description 1");
        testProducts.add(product);

        // Act
        ResultActions response = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));
    }

    /**
     * Test case for retrieving all products.
     * Verifies that the API returns a list of products with HTTP status OK.
     */
    @Test
    void testGetAllProducts() throws Exception {
        // Arrange
        List<ProductEntity> products = new ArrayList<>();
        products.add(new ProductEntity(null, "Product 1", "10.00", "Description 1"));
        products.add(new ProductEntity(null, "Product 2", "20.00", "Description 2"));
        testProducts.addAll(productRepository.saveAll(products));

        // Act
        ResultActions response = mockMvc.perform(get("/products"));

        // Assert
        response.andExpect(status().isOk());
    }

    /**
     * Test case for retrieving a product by ID.
     * Verifies that the API returns the product with the specified ID and HTTP status FOUND.
     */
    @Test
    void testGetProductById() throws Exception {
        // Arrange
        ProductEntity product = new ProductEntity(null, "Product 1", "10.00", "Description 1");
        ProductEntity savedProduct = productRepository.save(product);
        testProducts.add(savedProduct);

        // Act
        ResultActions response = mockMvc.perform(get("/products/{id}", savedProduct.getId()));

        // Assert
        response.andExpect(status().isFound())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));
    }

    /**
     * Test case for updating a product.
     * Verifies that the API returns the updated product with HTTP status OK.
     */
    @Test
    void testUpdateProduct() throws Exception {
        // Arrange
        ProductEntity product = new ProductEntity(null, "Product 1", "10.00", "Description 1");
        ProductEntity savedProduct = productRepository.save(product);
        testProducts.add(savedProduct);

        ProductEntity updatedProduct = new ProductEntity(savedProduct.getId(), "Updated Product", "20.00", "Updated Description");

        // Act
        ResultActions response = mockMvc.perform(patch("/products/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedProduct.getName())))
                .andExpect(jsonPath("$.price", is(updatedProduct.getPrice())))
                .andExpect(jsonPath("$.description", is(updatedProduct.getDescription())));
    }

    /**
     * Test case for deleting a product.
     * Verifies that the API returns HTTP status NO_CONTENT after deleting the product.
     */
    @Test
    void testDeleteProduct() throws Exception {
        // Arrange
        ProductEntity product = new ProductEntity(null, "Product 1", "10.00", "Description 1");
        ProductEntity savedProduct = productRepository.save(product);
        testProducts.add(savedProduct);

        // Act
        ResultActions response = mockMvc.perform(delete("/products/{id}", savedProduct.getId()));

        // Assert
        response.andExpect(status().isNoContent());
    }
}