package com.project.indytskyi.tripsservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${car.url}")
    private String basicUrlCar;

    @Value("${backOffice.url}")
    private String basicUrlBackOffice;

    @Value("${user.url}")
    private String basicUrlUser;

    @Bean
    public WebClient carWebClient() {
        return WebClient.builder()
                .baseUrl(basicUrlCar)
                .build();
    }

    @Bean
    public WebClient backOfficeWebClient() {
        return WebClient.builder()
                .baseUrl(basicUrlBackOffice)
                .build();
    }

    @Bean WebClient userWebClient() {
        return WebClient.builder()
                .baseUrl(basicUrlUser)
                .build();
    }

}
