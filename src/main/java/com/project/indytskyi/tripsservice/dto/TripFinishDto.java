package com.project.indytskyi.tripsservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * Class which contains data
 * for car and backoffice service
 * after finished order
 */
@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TripFinishDto {
    @ApiModelProperty(example = "3.567", value = "")
    private double latitude;

    @ApiModelProperty(example = "-4.6587", value = "")
    private double longitude;

    @ApiModelProperty(example = "300.0", value = "")
    private double tripPayment;

    @ApiModelProperty(example = "300.0", value = "")
    private double balance;

    @ApiModelProperty(example = "12", value = "")
    private long carId;

    @ApiModelProperty(example = "22", value = "")
    private long userId;

    @ApiModelProperty(example = "5.0", value = "")
    private double distance;

}
