package com.github.alex3g.product_ms.service;

import com.github.alex3g.product_ms.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDTO> create(ProductDTO request);

    List<ProductDTO> getAll();

    Optional<ProductDTO> getById(Long id);

    boolean inactive(Long id);

    Optional<ProductDTO> update(Long id, ProductDTO request);
}
