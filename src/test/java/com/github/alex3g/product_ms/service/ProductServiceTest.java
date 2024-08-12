package com.github.alex3g.product_ms.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.github.alex3g.product_ms.dto.ProductDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:clear-database.sql"})
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @BeforeAll
    static void setUp() {
        FixtureFactoryLoader.loadTemplates("com.github.alex3g.product_ms.template");
    }

    @Test
    void shouldCreateProduct() {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");

        assertEquals(productToCreate.getName(), createdProduct.get().getName(), "Product name should match");
        assertEquals(productToCreate.getDescription(), createdProduct.get().getDescription(), "Product description should match");
        assertEquals(productToCreate.getPrice(), createdProduct.get().getPrice(), "Product price should match");
        assertTrue(createdProduct.get().isAvailable(), "Product availability should be true");
    }

    @Test
    void shouldGetAllProducts() {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");

        List<ProductDTO> fetchedProducts = service.getAll();

        // Assert: Validate the fetched products
        assertNotNull(fetchedProducts, "Fetched products shouldn't be null");

        assertEquals(createdProduct.get().getName(), fetchedProducts.getFirst().getName(), "Product name should match");
        assertEquals(createdProduct.get().getDescription(), fetchedProducts.getFirst().getDescription(), "Product description should match");
        assertEquals(createdProduct.get().getPrice(), fetchedProducts.getFirst().getPrice(), "Product price should match");
    }

    @Test
    void shouldGetProductById() {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");

        // Get the id of created product
        Long id = createdProduct.get().getId();

        // Act: Fetch the product by its ID
        Optional<ProductDTO> fetchedProduct = service.getById(id);

        // Assert: Validate fetched product
        assertTrue(fetchedProduct.isPresent(), "Fetched product should be present");

        assertEquals(productToCreate.getName(), fetchedProduct.get().getName(), "Product name should match");
        assertEquals(productToCreate.getDescription(), fetchedProduct.get().getDescription(), "Product description should match");
        assertEquals(productToCreate.getPrice(), fetchedProduct.get().getPrice(), "Product price should match");
    }

    @Test
    void shouldUpdateProduct() {
        ProductDTO productToUpdate = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToUpdate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");

        // Get the id of created product
        Long id = createdProduct.get().getId();

        // Update
        String newName = "Monitor";
        String newDescription = "Esse com certeza é o melhor monitor LCD já criado pela empresa DELL desde os anos 2000. Monitor da marca DELL de 30 polegadas";
        double newPrice = 7889.99;
        productToUpdate.setName(newName);
        productToUpdate.setDescription(newDescription);
        productToUpdate.setPrice(newPrice);

        Optional<ProductDTO> updatedProductDTO = service.update(id, productToUpdate);

        assertTrue(updatedProductDTO.isPresent(), "Product update should updated successfully");

        assertEquals(newName, updatedProductDTO.get().getName(), "Product name should match");
        assertEquals(newDescription, updatedProductDTO.get().getDescription(), "Product description should match");
        assertEquals(newPrice, updatedProductDTO.get().getPrice(), "Product price should match");
    }

    @Test
    void shouldInactiveProduct() {
        ProductDTO productToInactive = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToInactive);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");
        Long id = createdProduct.get().getId();

        boolean inactive = service.inactive(id);

        assertTrue(inactive, "Product availability should be true");
    }
}
