package com.project.indytskyi.TripsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * Class in which we will receive constant data about the current coordinate of the car
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentCoordinatesDTO {
    @Range(min = -90, max = 90, message = "For latitude, use values in the range -90 to 90")
    private double latitude;
    @Range(min = -180, max = 180, message = "For latitude, use values in the range -180 to 180")
    private double longitude;
}
