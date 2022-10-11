package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.services.BackOfficeService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class BackOfficeServiceImpl implements BackOfficeService {

    private static final String BEARER_TOKEN_START = "Bearer ";
    private final WebClient backOfficeWebClient;

    @Override
    public Double getCarTariff(String carClass, String token) {

        log.info("get from backoffice-service tariff by casClass = {}",
                carClass);
        String ratePerHour = backOfficeWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("tariffs/" + carClass)
                        .queryParams()
                        .build())
                .header("Authorization", BEARER_TOKEN_START + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Double.valueOf(ratePerHour.replaceAll("\\D+",""));
    }

}
