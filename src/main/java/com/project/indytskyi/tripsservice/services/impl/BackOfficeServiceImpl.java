package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.backoffice.ResponseFromBackofficeDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.services.BackOfficeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class BackOfficeServiceImpl implements BackOfficeService {

    private static final String BEARER_TOKEN_START = "Bearer ";
    private final WebClient backOfficeWebClient;

    @Override
    public double getCarTariff(CarDto carDto, String token) {

        log.info("get from backoffice-service tariff by casClass = {}",
                carDto.getCarClass());
        ResponseFromBackofficeDto backofficeDto = backOfficeWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("tariffs/" + carDto.getCarClass())
                        .queryParam("latitude", carDto.getCoordinates().getLatitude())
                        .queryParam("longitude", carDto.getCoordinates().getLongitude())
                        .build())
                .header("Authorization", BEARER_TOKEN_START + token)
                .retrieve()
                .bodyToMono(ResponseFromBackofficeDto.class)
                .block();

        return backofficeDto.getRatePerHour();
    }

}
