package com.github.alex3g.product_ms.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

//    @Bean
//    public OpenApiResource openApiResource() {
//        return new OpenApiResource() {
//            @Override
//            protected String getServerUrl(HttpServletRequest request, String apiDocsUrl) {
//                return "";
//            }
//        };
//    }
}
