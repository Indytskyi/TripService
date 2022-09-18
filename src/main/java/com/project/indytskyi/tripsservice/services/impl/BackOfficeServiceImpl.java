package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.BackOfficeDto;
import com.project.indytskyi.tripsservice.services.BackOfficeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BackOfficeServiceImpl implements BackOfficeService {

    private final WebClient client = WebClient.create("http://localhost:8083/user/");

    @Override
    public Double getCarTariff(String carClass) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("tariff")
                        .queryParam(carClass)
                        .build())
                .retrieve()
                .bodyToMono(Double.class)
                .block();
    }

    @Override
    public void sendOrder(BackOfficeDto backOfficeDto) {
        Object response = client.post()
                .uri("order")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(backOfficeDto))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
