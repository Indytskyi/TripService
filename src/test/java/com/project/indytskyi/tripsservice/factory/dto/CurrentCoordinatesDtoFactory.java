package com.project.indytskyi.tripsservice.factory.dto;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;

public class CurrentCoordinatesDtoFactory {
    public static final long CURRENT_COORDINATES_TRAFFIC_ORDER_ID = 3;
    public static final double CURRENT_COORDINATES_LATITUDE = 4; //4.5
    public static final double CURRENT_COORDINATES_LONGITUDE = 3; //3.5
    public static final double CURRENT_COORDINATES_LONGITUDE_INVALID = 200;
    public static final double CURRENT_COORDINATES_LATITUDE_INVALID = 200;

    public static CurrentCoordinatesDto createCurrentCoordinatesDto() {
        return  CurrentCoordinatesDto.of()
                .latitude(CURRENT_COORDINATES_LATITUDE)
                .longitude(CURRENT_COORDINATES_LONGITUDE)
                .tripId(CURRENT_COORDINATES_TRAFFIC_ORDER_ID)
                .build();
    }

    public static CurrentCoordinatesDto currentCoordinatesDtoForSavingWithInvalidLatitude() {
        return CurrentCoordinatesDto.of()
                .latitude(CURRENT_COORDINATES_LATITUDE_INVALID)
                .longitude(CURRENT_COORDINATES_LONGITUDE)
                .tripId(CURRENT_COORDINATES_TRAFFIC_ORDER_ID)
                .build();
    }

    public static CurrentCoordinatesDto currentCoordinatesDtoForSavingWithInvalidLongitude() {
        return CurrentCoordinatesDto.of()
                .latitude(CURRENT_COORDINATES_LATITUDE)
                .longitude(CURRENT_COORDINATES_LONGITUDE_INVALID)
                .tripId(CURRENT_COORDINATES_TRAFFIC_ORDER_ID)
                .build();
    }

}
