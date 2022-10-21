package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.config.DiscoveryUrlConfig;
import com.project.indytskyi.tripsservice.dto.backoffice.CarTariffInformationDto;
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

    private final DiscoveryUrlConfig basicUrl;
    private final WebClient webClient;

    @Override
    public CarTariffInformationDto getCarTariffResponse(CarDto carDto, String token) {

        log.info("get from backoffice-service tariff by casClass = {}",
                carDto.getCarClass());

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(basicUrl.getBackofficeServiceUrl() + "/user/tariffs/"
                                + carDto.getCarClass())
                        .queryParam("latitude", carDto.getCoordinates().getLatitude())
                        .queryParam("longitude", carDto.getCoordinates().getLongitude())
                        .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(CarTariffInformationDto.class)
                .block();
    }

}
