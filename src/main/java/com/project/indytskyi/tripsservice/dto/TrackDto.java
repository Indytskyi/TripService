package com.project.indytskyi.tripsservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TrackDto {

    @ApiModelProperty(example = "18")
    private long id;

    @ApiModelProperty(example = "-4.6587")
    private double latitude;

    @ApiModelProperty()
    private double longitude;

    @ApiModelProperty()
    private int speed;

    @ApiModelProperty()
    private String unitOfSpeed;

    @ApiModelProperty()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @ApiModelProperty(example = "0.2")
    private double distance;

}
