package com.github.alex3g.product_ms.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alex3g.product_ms.dto.ProductDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        FixtureFactoryLoader.loadTemplates("com.github.alex3g.product_ms.template");
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductDTO request = Fixture.from(ProductDTO.class).gimme("valid");
        String productAsString = mapper.writeValueAsString(request);

        mvc.perform(post("/products")
                        .header(AUTHORIZATION, "Bearer foo")
                        .contentType(APPLICATION_JSON)
                        .content(productAsString))
                .andExpect(status().isCreated());
    }

}
