package com.dev.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name")
    @Setter
    private String name;

    @NotBlank(message = "Description is required")
    @Column(name = "description")
    @Setter
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(name = "price")
    @Setter
    private String price;

    public ProductEntity() {
        // Default constructor required by JPA
    }

    public ProductEntity(Long id, String name, String price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public ProductEntity merge(final ProductEntity other) {
        this.setName(other.getName());
        this.setDescription(other.getDescription());
        this.setPrice(other.getPrice());
        return this;
    }
}
