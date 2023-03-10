package com.project.indytskyi.tripsservice.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * Class in which we will receive constant data about the current coordinate of the car.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class CurrentCoordinatesDto {

    @ApiModelProperty(example = "3", value = "")
    @Min(value = 1, message = "trafficOrderId must have correct data")
    private long tripId;

    @ApiModelProperty(example = "3.567", value = "")
    @Range(min = -90, max = 90, message = "For latitude, use values in the range -90 to 90")
    private double latitude;

    @ApiModelProperty(example = "-4.6587", value = "")
    @Range(min = -180, max = 180, message = "For longitude, use values in the range -180 to 180")
    private double longitude;

}
