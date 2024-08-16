package com.github.alex3g.product_ms.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.alex3g.product_ms.util.Views;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO {
    @JsonView(Views.Internal.class)
    private Long id;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Name is required")
    private String name;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Description is required")
    @Size(min = 50)
    private String description;

    @JsonView(Views.Public.class)
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private double price;

    @JsonView(Views.Internal.class)
    private boolean isAvailable;
}
