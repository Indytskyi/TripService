package com.project.indytskyi.tripsservice.factory.dto.car;

import com.project.indytskyi.tripsservice.dto.car.CarUpdateInfoAfterTripDto;
import com.project.indytskyi.tripsservice.dto.car.StartCoordinatesOfCarDto;

public class CarUpdateInfoAfterTripDtoFactory {

    private static final String CAR_UPDATE_CAR_STATUS = "FREE";
    private static final StartCoordinatesOfCarDto CAR_UPDATE_COORDINATES =
            new StartCoordinatesOfCarDto(23, 23);
    private static final double CAR_UPDATE_DISTANCE = 40;
    private static final double CAR_UPDATE_FUEL_LEVEL = 20;

    public static CarUpdateInfoAfterTripDto createCarUpdateDto() {
        return CarUpdateInfoAfterTripDto.of()
                .carStatus(CAR_UPDATE_CAR_STATUS)
                .coordinates(CAR_UPDATE_COORDINATES)
                .distanceInKilometers(CAR_UPDATE_DISTANCE)
                .fuelLevel(CAR_UPDATE_FUEL_LEVEL)
                .build();
    }
}
