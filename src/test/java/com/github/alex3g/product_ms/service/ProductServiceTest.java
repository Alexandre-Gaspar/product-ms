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
    void shouldCreateProduct() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");

        assertEquals(productToCreate.getName(), createdProduct.get().getName(), "Product name should match");
        assertEquals(productToCreate.getDescription(), createdProduct.get().getDescription(), "Product description should match");
        assertEquals(productToCreate.getPrice(), createdProduct.get().getPrice(), "Product price should match");
        assertTrue(createdProduct.get().isAvailable(), "Product availability should be true");
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");

        List<ProductDTO> fetchedProducts = service.getAll();

        // Assert: Validate the fetched products
        assertNotNull(fetchedProducts, "Fetched products shouldn't be null");

        assertEquals(createdProduct.get().getName(), fetchedProducts.get(0).getName(), "Product name should match");
        assertEquals(createdProduct.get().getDescription(), fetchedProducts.get(0).getDescription(), "Product description should match");
        assertEquals(createdProduct.get().getPrice(), fetchedProducts.get(0).getPrice(), "Product price should match");
    }

    @Test
    void shouldGetProductById() throws Exception {
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
    void shouldNotGetProductById() throws Exception {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(request);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully.");

        Long nonExistentId = -1L;

        // Assert: Guarantees that both IDs doesn't match
        assertNotEquals(createdProduct.get().getId(), nonExistentId, "The generated ID shouldn't be match the created product ID");

        // Try fetch a product with non-existent id
        Optional<ProductDTO> fetchedProduct = service.getById(nonExistentId);

        // Assert: Guarantees that no product will be returned
        assertFalse(fetchedProduct.isPresent(), "Product with non-existent ID should not be fetched.");

    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid-update");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");

        // Get created product id
        Long id = createdProduct.get().getId();

        // Update
        ProductDTO productToUpdate = Fixture.from(ProductDTO.class).gimme("valid-update");
        Optional<ProductDTO> updatedProductDTO = service.update(id, productToUpdate);

        assertTrue(updatedProductDTO.isPresent(), "Product update should updated successfully");

        assertEquals(productToUpdate.getName(), updatedProductDTO.get().getName(), "Product name should match");
        assertEquals(productToUpdate.getDescription(), updatedProductDTO.get().getDescription(), "Product description should match");
        assertEquals(productToUpdate.getPrice(), updatedProductDTO.get().getPrice(), "Product price should match");
    }

    @Test
    void shouldNotUpdateProduct() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid-update");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully.");

        Long nonExistentId = -1L;

        // Assert: Guarantees that non-existent ID isn't the same that created product
        assertNotEquals(createdProduct.get().getId(), nonExistentId, "The generated id should not be match the created product ID");

        // Try to update a product with a non-existent ID
        ProductDTO productToUpdate = Fixture.from(ProductDTO.class).gimme("valid-update");
        Optional<ProductDTO> updatedProduct = service.update(nonExistentId, productToUpdate);

        // Assert: Verify if the product was not updated
        assertFalse(updatedProduct.isPresent(), "Expected that the product doesn't update with a non-existent ID");
    }

    @Test
    void shouldInactiveProduct() throws Exception {
        ProductDTO productToInactive = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToInactive);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully");
        Long id = createdProduct.get().getId();

        boolean inactive = service.inactive(id);

        assertTrue(inactive, "Product availability should be true");
    }

    @Test
    void shouldNotInactiveProduct() throws Exception {
        ProductDTO productToCreate = Fixture.from(ProductDTO.class).gimme("valid");
        Optional<ProductDTO> createdProduct = service.create(productToCreate);

        assertTrue(createdProduct.isPresent(), "Product should be created successfully.");;

        Long nonExistentId = -1L;

        // Assert: Guarantees that both IDs doesn't match
        assertNotEquals(createdProduct.get().getId(), nonExistentId, "The generated ID shouldn't be match the created product ID");

        // Attempt to inactivate a product with a non-existent ID
        boolean result = service.inactive(nonExistentId);

        // Assert: Guarantees that no product will be returned
        assertFalse(result, "Inactive operation should return false for non-existent product ID.");
    }
}
