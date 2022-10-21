package com.project.indytskyi.tripsservice.factory.dto;

import com.project.indytskyi.tripsservice.dto.TripFinishDto;

public class TripFinishDtoFactory {
    public static final double TRIP_FINISH_DTO_LATITUDE = 4;
    public static final double TRIP_FINISH_DTO_LONGITUDE = 3;
    public static final double TRIP_FINISH_DTO_TRIP_PAYMENT = 300;
    public static final long TRIP_FINISH_DTO_CAR_ID = 12;
    public static final long TRIP_FINISH_DTO_USER_ID = 22;
    public static final double TRIP_FINISH_DTO_DISTANCE = 0;
    public static final String TRIP_FINISH_DTO_UNIT_OF_DISTANCE = "kilometres";

        public static TripFinishDto createTripFinishDto() {
        return TripFinishDto.of()
                .latitude(TRIP_FINISH_DTO_LATITUDE)
                .longitude(TRIP_FINISH_DTO_LONGITUDE)
                .tripPayment(TRIP_FINISH_DTO_TRIP_PAYMENT)
                .carId(TRIP_FINISH_DTO_CAR_ID)
                .userId(TRIP_FINISH_DTO_USER_ID)
                .distance(TRIP_FINISH_DTO_DISTANCE)
                .unitOfDistance(TRIP_FINISH_DTO_UNIT_OF_DISTANCE)
                .build();
    }
}
