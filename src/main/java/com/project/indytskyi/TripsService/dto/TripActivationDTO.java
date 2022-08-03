package com.project.indytskyi.TripsService.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 *  Class in which we will receive constant data from the car service
 */
@Data
public class TripActivationDTO {
    @Min(value = 1, message = "CarId must have correct data")
    private long carId;
    @Min(value = 1, message = "UserId must have correct data")
    private long userId;
    @Min(value = 10, message = "Balance must have more than 10 hryvnias on the account")
    private double balance;
    @Range(min = -90, max = 90, message = "For latitude, use values in the range -90 to 90")
    private double latitude;
    @Range(min = -180, max = 180, message = "For latitude, use values in the range -180 to 180")
    private double longitude;
}
