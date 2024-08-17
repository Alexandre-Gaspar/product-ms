package com.github.alex3g.product_ms.service.impl;

import com.github.alex3g.product_ms.dto.ProductDTO;
import com.github.alex3g.product_ms.exception.custom.ProductValidationException;
import com.github.alex3g.product_ms.model.Product;
import com.github.alex3g.product_ms.repository.ProductRepository;
import com.github.alex3g.product_ms.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<ProductDTO> create(ProductDTO request) throws Exception {
        request.setAvailable(true);
        Product product = mapper.map(request, Product.class);
        validateProduct(product.getName());
        this.repository.saveAndFlush(product);

        // create response
        return Optional.of(mapper.map(product, ProductDTO.class));
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = this.repository.findAll();
        List<ProductDTO> responses = new ArrayList<>();

        // for each product that has in db, add to response list using a dto to show it
        products.forEach(product -> responses.add(mapper.map(product, ProductDTO.class)));

        return responses;
    }

    @Override
    public Optional<ProductDTO> getById(Long id) {
        Optional<Product> productFound = this.repository.findById(id);

        // if product is present, returns a map of products else return an empty optional
        return productFound.map(product -> mapper.map(product, ProductDTO.class));
    }

    @Override
    public boolean inactive(Long id) {
        Optional<Product> product = this.repository.findById(id);
        if (product.isPresent()) {
            product.get().setAvailable(false);
            this.repository.save(product.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<ProductDTO> update(Long id, ProductDTO request) {
        Optional<Product> product = this.repository.findById(id);
        if (product.isPresent()) {
            product.get().setName(request.getName());
            product.get().setDescription(request.getDescription());
            product.get().setPrice(request.getPrice());
            this.repository.save(product.get());

            return Optional.of(mapper.map(product.get(), ProductDTO.class));
        }

        return Optional.empty();
    }

    public void validateProduct(String name) throws Exception{
        Optional<Product> fetchedProductByName = this.repository.findByName(name);

        if (fetchedProductByName.isPresent()) {
            throw new ProductValidationException("Already exists a product with this name");
        }

    }
}
