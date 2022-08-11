package com.project.indytskyi.tripsservice.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 *  Class in which we will receive constant data from the car service
 */
@Data
public class TripActivationDto {

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
    @Positive(message = "Pay per hour should be more than zero")
    private double tariff;

}
