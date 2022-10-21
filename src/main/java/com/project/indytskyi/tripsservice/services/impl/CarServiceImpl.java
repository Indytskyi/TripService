package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.config.DiscoveryUrlConfig;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.services.CarService;
import com.project.indytskyi.tripsservice.util.enums.CarStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final DiscoveryUrlConfig basicUrl;
    private final WebClient webClient;

    @Override
    public CarDto getCarInfo(TripActivationDto tripActivationDto, String token) {

        log.info("get car from Car-service, carId = {}", tripActivationDto.getCarId());

        return webClient
                .get()
                .uri(basicUrl.getCarServiceUrl() + "/cars/"
                        + String.valueOf(tripActivationDto.getCarId()))
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(CarDto.class)
                .share()
                .block();
    }

    @Override
    public void setCarStatus(long carId, String token) {
        log.info("set status to car by carId = {}", carId);

        Object o = webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(basicUrl.getCarServiceUrl() + "/cars/status/" + carId)
                        .queryParam("carStatus", CarStatus.RENTED.name())
                        .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Object.class)
                .share()
                .block();
    }

}
