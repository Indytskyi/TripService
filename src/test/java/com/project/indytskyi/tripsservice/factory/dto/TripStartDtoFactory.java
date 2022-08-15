package com.project.indytskyi.tripsservice.factory.dto;

import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.util.Status;
import com.project.indytskyi.tripsservice.util.StatusPaid;

public class TripStartDtoFactory {
    private static final long TRIP_START_OWNER_ID = 22L;
    private static final long TRIP_START_CAR_ID = 12L;
    private static final long TRIP_START_USER_ID = 22L;
    private static final String TRIP_START_STATUS = String.valueOf(Status.IN_ORDER);
    private static final String TRIP_START_STATUS_PAID = String.valueOf(StatusPaid.IN_PROCESS);
    private static final double TRIP_START_BALANCE = 600;
    private static final long TRIP_START_TRACK_ID = 27L;
    private static final double TRIP_ACTIVATION_LATITUDE = 4;
    private static final double TRIP_START_LONGITUDE = 3;
    private static final int TRIP_START_SPEED = 0;
    private static final double TRIP_START_DISTANCE = 0;
    private static final double TRIP_START_TARIFF = 300;

    public static TripStartDto createTripStartDto() {
        return TripStartDto.of()
                .ownerId(TRIP_START_OWNER_ID)
                .carId(TRIP_START_CAR_ID)
                .userId(TRIP_START_USER_ID)
                .status(TRIP_START_STATUS)
                .statusPaid(TRIP_START_STATUS_PAID)
                .balance(TRIP_START_BALANCE)
                .trackId(TRIP_START_TRACK_ID)
                .latitude(TRIP_ACTIVATION_LATITUDE)
                .longitude(TRIP_START_LONGITUDE)
                .speed(TRIP_START_SPEED)
                .distance(TRIP_START_DISTANCE)
                .tariff(TRIP_START_TARIFF)
                .build();
    }

}
