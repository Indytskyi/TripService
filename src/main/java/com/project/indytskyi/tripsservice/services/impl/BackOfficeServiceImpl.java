package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.backoffice.BackOfficeDto;
import com.project.indytskyi.tripsservice.services.BackOfficeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class BackOfficeServiceImpl implements BackOfficeService {

    private final WebClient backOfficeWebClient;

    @Override
    public Double getCarTariff(String carClass) {
        log.info("get from backoffice-service tariff by casClass = {}",
                carClass);
        String ratePerHour = backOfficeWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("tariffs/" + carClass)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Double.valueOf(ratePerHour.replaceAll("\\D+",""));
    }

    @Override
    public void sendOrder(BackOfficeDto backOfficeDto) {
        log.info("send final information about trip trip to backoffice-service");
        Object response = backOfficeWebClient.post()
                .uri("order")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(backOfficeDto))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
