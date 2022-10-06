package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.services.CarService;
import com.project.indytskyi.tripsservice.util.enums.CarStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final WebClient carWebClient;

    @Override
    public CarDto getCarInfo(TripActivationDto tripActivationDto) {

        log.info("get car from Car-service, carId = {}", tripActivationDto.getCarId());

        return carWebClient
                .get()
                .uri(String.valueOf(tripActivationDto.getCarId()))
                .retrieve()
                .bodyToMono(CarDto.class)
                .share()
                .block();
    }

    //TODO refactor this
    @Override
    @Transactional
    public void setCarStatus(long carId) {
        log.info("set status to car by carId = {}", carId);
        String h = String.valueOf(CarStatus.RENTED);
        Object o = carWebClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path("status/" + carId)
                        .queryParam("carStatus", h)
                        .build())
                .retrieve()
                .bodyToMono(Object.class)
                .share()
                .block();
    }

}
