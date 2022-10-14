package com.project.indytskyi.tripsservice.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 *  Class in which we will receive constant data from the car service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TripActivationDto {

    @ApiModelProperty(example = "12", value = "")
    @Min(value = 1, message = "CarId must have correct data")
    private long carId;

    @ApiModelProperty(example = "22", value = "")
    @Min(value = 1, message = "UserId must have correct data")
    private long userId;

    @ApiModelProperty(example = "3.567", value = "")
    @Range(min = -90, max = 90, message = "For latitude, use values in the range -90 to 90")
    private double latitude;

    @ApiModelProperty(example = "-4.6587", value = "")
    @Range(min = -180, max = 180, message = "For latitude, use values in the range -180 to 180")
    private double longitude;

    @ApiModelProperty(example = "300.0", value = "")
    private double tariff;

    @ApiModelProperty(example = "USD")
    private String currency;

}
