package com.github.alex3g.product_ms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(
            description = "Unique identifier of the product",
            example = "1"
    )
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(
            description = "Name of the product",
            example = "Monitor DELL"
    )
    private String name;

    @Column(name = "description", nullable = false)
    @Schema(
            description = "Description of the product (Minimum size is 50 characters)",
            example = "The monitor screen is 32 inches, with built-in speaker and is touch screen"
    )
    private String description;

    @Schema(
            description = "Price of the product",
            example = "Must be a value > 0: 0.01"
    )
    @Column(name = "price")
    private double price;

    @Column(name = "is_available")
    @Schema(
            description = "Indicates the availability of the product, by default, all created product is true",
            example = "true"
    )
    private boolean isAvailable;
}
