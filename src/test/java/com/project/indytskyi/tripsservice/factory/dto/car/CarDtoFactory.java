package com.project.indytskyi.tripsservice.factory.dto.car;

import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.dto.car.StartCoordinatesOfCarDto;

public class CarDtoFactory {

    private final static String CAR_DTO_CAR_CLASS = "MEDIUM";
    private final static StartCoordinatesOfCarDto CAR_DTO_COORDINATES =
            new StartCoordinatesOfCarDto(49.87, 24.15);

    public static CarDto createCarDto() {
        return  CarDto.of()
                .carClass(CAR_DTO_CAR_CLASS)
                .coordinates(CAR_DTO_COORDINATES)
                .build();
    }
}
