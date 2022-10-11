package com.project.indytskyi.tripsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String PATH_TO_CONTROLLERS =
            "com.project.indytskyi.tripsservice.controllers";

    /**
     * Configuration swagger in spring,
     * and share for him path to controllers
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage(PATH_TO_CONTROLLERS))
                .paths(PathSelectors.any())
                .build();
    }

}
