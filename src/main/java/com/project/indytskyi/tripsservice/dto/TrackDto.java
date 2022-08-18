package com.project.indytskyi.tripsservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TrackDto {
    @ApiModelProperty(example = "18", value = "")
    private long id;

    @ApiModelProperty(example = "3.567", value = "")
    @JsonIgnore
    private TrafficOrderEntity ownerTrack;

    @ApiModelProperty(example = "-4.6587", value = "")
    private double latitude;

    @ApiModelProperty(value = "")
    private double longitude;

    @ApiModelProperty(value = "")
    private int speed;

    @ApiModelProperty(value = "")
    private LocalDateTime timestamp;

    @ApiModelProperty(example = "0.2", value = "")
    private double distance;

}
