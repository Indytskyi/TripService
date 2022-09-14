package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Override
    public String getCarInfo(TripActivationDto tripActivationDto) {
        CarDto carDto = WebClient
                .create("http://localhost:8081/cars/" + tripActivationDto.getCarId())
                .get()
                .retrieve()
                .bodyToMono(CarDto.class)
                .share()
                .block();

        tripActivationDto.setLongitude(carDto.getCoordinates().getLongitude());
        tripActivationDto.setLatitude(carDto.getCoordinates().getLatitude());
        return carDto.getCarClass();
    }
}
