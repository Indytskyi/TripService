package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.dto.car.CarUpdateInfoAfterTripDto;
import com.project.indytskyi.tripsservice.dto.car.StartCoordinatesOfCarDto;
import com.project.indytskyi.tripsservice.services.CarService;
import com.project.indytskyi.tripsservice.util.CarStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final WebClient carWebClient;

    @Override
    public String getCarInfo(TripActivationDto tripActivationDto) {

        log.info("get car from Cas-service, carId = {}", tripActivationDto.getCarId());

        CarDto carDto = carWebClient
                .get()
                .uri(String.valueOf(tripActivationDto.getCarId()))
                .retrieve()
                .bodyToMono(CarDto.class)
                .share()
                .block();

        tripActivationDto.setLongitude(carDto.getCoordinates().getLongitude());
        tripActivationDto.setLatitude(carDto.getCoordinates().getLatitude());
        return carDto.getCarClass();
    }

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

    @Override
    public void setCarAfterFinishingOrder(TripFinishDto tripFinishDto) {

        log.info("set information to car after the end of the trip");

        CarUpdateInfoAfterTripDto carUpdateInfoAfterTripDto = new CarUpdateInfoAfterTripDto();
        carUpdateInfoAfterTripDto.setCarStatus(String.valueOf(CarStatus.FREE));
        carUpdateInfoAfterTripDto
                .setCoordinates(new StartCoordinatesOfCarDto(tripFinishDto.getLatitude(),
                        tripFinishDto.getLongitude()));
        carUpdateInfoAfterTripDto.setDistanceInKilometers(tripFinishDto.getDistance());
        carUpdateInfoAfterTripDto.setFuelLevelLiter(5);

        Object response = carWebClient.post()
                .uri(String.valueOf(tripFinishDto.getCarId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(carUpdateInfoAfterTripDto))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

}
