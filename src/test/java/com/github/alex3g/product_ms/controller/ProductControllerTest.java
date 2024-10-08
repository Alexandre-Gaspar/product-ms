package com.github.alex3g.product_ms.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alex3g.product_ms.dto.ProductDTO;
import com.github.alex3g.product_ms.model.Product;
import com.github.alex3g.product_ms.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeAll
    static void setUp() {
        FixtureFactoryLoader.loadTemplates("com.github.alex3g.product_ms.template");
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        String productDataAsString = objectMapper.writeValueAsString(request);

        mvc.perform(post("/products")
                        .header(AUTHORIZATION, "Bearer foo")
                        .contentType(APPLICATION_JSON)
                        .content(productDataAsString))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreateProduct() throws Exception {
        ProductDTO request = new ProductDTO(); // An empty request
        String productDataAsString = objectMapper.writeValueAsString(request);

        mvc.perform(post("/products")
                        .header(AUTHORIZATION, "Bearer foo")
                        .contentType(APPLICATION_JSON)
                        .content(productDataAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllProducts() throws Exception{
        mvc.perform(get("/products")
                        .header(AUTHORIZATION, "Bearer foo"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetById() throws Exception {
        Product productToCreate = Fixture.from(Product.class).gimme("valid");
        repository.save(productToCreate);

        Long id = repository.findAll().get(0).getId();

        mvc.perform(get("/products/{id}", id)
                        .header(AUTHORIZATION, "Bearer foo"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetById() throws Exception {
        Long nonExistentId = 999L;

        mvc.perform(get("/products/{id}", nonExistentId)
                        .header(AUTHORIZATION, "Bearer foo"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Product createdProduct = repository.save(modelMapper.map(productToCreate, Product.class));

        Long id = createdProduct.getId();

        ProductDTO productToUpdate = Fixture.from(ProductDTO.class).gimme("valid-update");
        String productDataToUpdateAsString = objectMapper.writeValueAsString(productToUpdate);

        mvc.perform(put("/products/{id}", id)
                        .header(AUTHORIZATION, "Bearer foo")
                        .contentType(APPLICATION_JSON)
                        .content(productDataToUpdateAsString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(productToUpdate.getName()))
                .andExpect(jsonPath("$.description").value(productToUpdate.getDescription()))
                .andExpect(jsonPath("$.price").value(productToUpdate.getPrice()));
    }

    @Test
    void shouldNotUpdateProduct() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid-update");
        Product createdProduct = repository.save(modelMapper.map(productToCreate, Product.class));

        Long nonExistentId = 999L;

        ProductDTO productToUpdate = Fixture.from(ProductDTO.class).gimme("valid-update");
        String productDataToUpdateAsString = objectMapper.writeValueAsString(productToUpdate);

        mvc.perform(put("/products/{id}", nonExistentId)
                        .header(AUTHORIZATION, "Bearer foo")
                        .contentType(APPLICATION_JSON)
                        .content(productDataToUpdateAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldInactiveProduct() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Product createdProduct = repository.save(modelMapper.map(productToCreate, Product.class));

        Long id = createdProduct.getId();

        mvc.perform(delete("/products/{id}", id)
                        .header(AUTHORIZATION, "Bearer foo"))
                .andExpect(status().isNoContent());

        Optional<Product> deletedProduct = repository.findById(id);
        assertTrue(deletedProduct.isPresent(), "Product should still exist in the database");
        assertFalse(deletedProduct.get().isAvailable(), "Product should be inactive");
    }

    @Test
    void shouldNotInactiveProduct() throws Exception {
        Long nonExistentId = 999L;

        mvc.perform(delete("/products/{id}", nonExistentId)
                        .header(AUTHORIZATION, "Bearer foo"))
                .andExpect(status().isNotFound());
    }

}
