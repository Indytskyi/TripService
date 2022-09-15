package com.project.indytskyi.tripsservice.dto.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarUpdateInfoAfterTripDto {
    private String carStatus;
    private StartCoordinatesOfCarDto coordinates;
    private double distanceInKilometers;
    private double fuelLevel;
}
