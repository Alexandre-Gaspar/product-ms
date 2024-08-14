package com.github.alex3g.product_ms.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigTest {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
