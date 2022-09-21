package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.dto.car.CarUpdateInfoAfterTripDto;
import com.project.indytskyi.tripsservice.dto.car.StartCoordinatesOfCarDto;
import com.project.indytskyi.tripsservice.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final WebClient client;

    @Value("${basicUrlForCarService}")
    private String basicUrl;

    @Override
    public String getCarInfo(TripActivationDto tripActivationDto) {

        log.info("get car from Cas-service, carId = {}", tripActivationDto.getCarId());

        CarDto carDto = client
                .get()
                .uri(basicUrl
                       + String.valueOf(tripActivationDto.getCarId()))
                .retrieve()
                .bodyToMono(CarDto.class)
                .share()
                .block();

        tripActivationDto.setLongitude(carDto.getCoordinates().getLongitude());
        tripActivationDto.setLatitude(carDto.getCoordinates().getLatitude());
        return carDto.getCarClass();
    }

    @Override
    public void setCarStatus(long carId) {

//        Object o = client.put()
//                .uri(String.valueOf(carId))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(jsonObject))
//                .retrieve()
//                .bodyToMono(Object.class)
//                .block();
    }

    @Override
    public void setCarAfterFinishingOrder(TripFinishDto tripFinishDto, long carId) {

        log.info("set information to car after the end of the trip");

        CarUpdateInfoAfterTripDto carUpdateInfoAfterTripDto = new CarUpdateInfoAfterTripDto();
        carUpdateInfoAfterTripDto.setCarStatus("FREE");
        carUpdateInfoAfterTripDto.setCoordinates(new StartCoordinatesOfCarDto(tripFinishDto.getLatitude(),
                tripFinishDto.getLongitude()));
        carUpdateInfoAfterTripDto.setDistanceInKilometers(tripFinishDto.getDistance());
        carUpdateInfoAfterTripDto.setFuelLevel(5);

        Object response = client.post()
                .uri(basicUrl
                        + String.valueOf(carId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(carUpdateInfoAfterTripDto))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

}
