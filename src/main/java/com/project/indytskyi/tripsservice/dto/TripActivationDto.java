package com.project.indytskyi.tripsservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 *  Class in which we will receive constant data from the car service
 */
@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
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
