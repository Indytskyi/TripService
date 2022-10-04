package com.project.indytskyi.tripsservice.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TrafficOrderDto {
    @ApiModelProperty(example = "27", value = "")
    private long id;

    @ApiModelProperty(example = "12", value = "")
    private long carId;

    @ApiModelProperty(example = "22", value = "")
    private long userId;

    @ApiModelProperty(value = "")
    private LocalDateTime activationTime;

    @ApiModelProperty(value = "")
    private LocalDateTime completionTime;

    @ApiModelProperty(example = "IN_ORDER", value = "")
    private String status;

    @ApiModelProperty(example = "IN_PROCESS", value = "")
    private String statusPaid;

    @ApiModelProperty(example = "300.0", value = "")
    private double tariff;

    @ApiModelProperty(value = "")
    private List<String> photos;

}
