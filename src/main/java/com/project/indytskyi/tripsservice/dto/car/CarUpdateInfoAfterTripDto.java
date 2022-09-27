package com.project.indytskyi.tripsservice.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class CarUpdateInfoAfterTripDto {
    private String carStatus;
    private StartCoordinatesOfCarDto coordinates;
    private double distanceInKilometers;
    private double fuelLevel;
}
