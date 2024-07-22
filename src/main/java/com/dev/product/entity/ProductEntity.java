package com.dev.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entity class representing a product.
 */
@Validated
@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {

    /**
     * The unique identifier of the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    /**
     * The name of the product.
     * Must not be blank.
     */
    @NotBlank(message = "Name is required")
    @Column(name = "name")
    @Setter
    private String name;

    /**
     * The description of the product.
     * Must not be blank.
     */
    @NotBlank(message = "Description is required")
    @Column(name = "description")
    @Setter
    private String description;

    /**
     * The price of the product.
     * Must not be null and must be positive.
     */
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(name = "price")
    @Setter
    private String price;

    /**
     * Default constructor required by JPA.
     */
    public ProductEntity() {
        // Default constructor required by JPA
    }

    /**
     * Constructs a new ProductEntity with the specified properties.
     *
     * @param id          the unique identifier of the product
     * @param name        the name of the product
     * @param price       the price of the product
     * @param description the description of the product
     */
    public ProductEntity(Long id, String name, String price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    /**
     * Merges the properties of another ProductEntity into this entity.
     *
     * @param mergedProduct the other ProductEntity to merge properties from
     * @return the updated ProductEntity
     */
    public ProductEntity merge(final ProductEntity mergedProduct) {
        this.setName(mergedProduct.getName());
        this.setDescription(mergedProduct.getDescription());
        this.setPrice(mergedProduct.getPrice());
        return this;
    }
}