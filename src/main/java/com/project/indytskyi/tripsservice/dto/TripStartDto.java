package com.project.indytskyi.tripsservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * class TripStartDTO which contains all information
 * about new traffic order and start track
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TripStartDto {

    @ApiModelProperty(example = "27", value = "")
    private long tripId;

    @ApiModelProperty(example = "12", value = "")
    private long carId;

    @ApiModelProperty(example = "22", value = "")
    private long userId;

    @ApiModelProperty(value = "")
    @Valid
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime activationTime;

    @ApiModelProperty(example = "IN_ORDER", value = "")
    private String status;

    @ApiModelProperty(example = "300.0", value = "")
    private double tariff;

    @ApiModelProperty(example = "USD")
    private String currency;

    @ApiModelProperty(example = "17", value = "")
    private long trackId;

    @ApiModelProperty(example = "3.567", value = "")
    private double latitude;

    @ApiModelProperty(example = "-4.6587", value = "")
    private double longitude;

    @ApiModelProperty(value = "")
    private int speed;

    @ApiModelProperty()
    private String unitOfSpeed;

    @ApiModelProperty(value = "")
    @Valid
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @ApiModelProperty(example = "0.0", value = "")
    private double distance;

}
