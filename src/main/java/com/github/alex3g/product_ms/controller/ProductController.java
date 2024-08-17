package com.github.alex3g.product_ms.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.alex3g.product_ms.dto.ProductDTO;
import com.github.alex3g.product_ms.service.impl.ProductServiceImpl;
import com.github.alex3g.product_ms.util.Views;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl productService;
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Create a new product",
            description = "Create a new product in the system database"
    )
    @JsonView(Views.Public.class)
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO request) throws Exception {
        var response = this.productService.create(request);
        return response
                .map(productDTO -> new ResponseEntity<>(productDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all products"
    )
    @JsonView(Views.Internal.class)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(this.productService.getAll());
    }

    @Operation(
            summary = "Get a product by ID.",
            description = "Retrieves a product by its ID.",
            parameters = @Parameter(
                    name = "id",
                    description = "ID of the product to get",
                    required = true
            )
    )
    @GetMapping("/{id}")
    @JsonView(Views.Internal.class)
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        var response = this.productService.getById(id);

        return response
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Update a product.",
            description = "Updates an existent product by passing its ID.",
            parameters = @Parameter(
                    name = "id",
                    description = "ID of the product to update",
                    required = true
            )
    )
    @JsonView(Views.Public.class)
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,
                                             @RequestBody @Valid ProductDTO request) {
        var response = this.productService.update(id, request);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @Operation(
            summary = "Update a product.",
            description = "Updates an existent product by passing its ID.",
            parameters = @Parameter(
                    name = "id",
                    description = "ID of the product to deactivate",
                    required = true
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var inactive = this.productService.inactive(id);
        return inactive
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
