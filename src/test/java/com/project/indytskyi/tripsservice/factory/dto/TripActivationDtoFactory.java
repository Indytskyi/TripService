package com.project.indytskyi.tripsservice.factory.dto;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;

public class TripActivationDtoFactory {

    public static final long TRIP_ACTIVATION_CAR_ID = 12;
    public static final long TRIP_ACTIVATION_USER_ID = 22;
    public static final double TRIP_ACTIVATION_BALANCE = 600;
    public static final double TRIP_ACTIVATION_LATITUDE = 4;
    public static final double TRIP_ACTIVATION_LONGITUDE = 3;
    public static final double TRIP_ACTIVATION_TARIFF = 300;

    public static TripActivationDto createTripActivationDto() {
        return  TripActivationDto.of()
                .carId(TRIP_ACTIVATION_CAR_ID)
                .userId(TRIP_ACTIVATION_USER_ID)
                .balance(TRIP_ACTIVATION_BALANCE)
                .latitude(TRIP_ACTIVATION_LATITUDE)
                .longitude(TRIP_ACTIVATION_LONGITUDE)
                .tariff(TRIP_ACTIVATION_TARIFF)
                .build();
    }
}
