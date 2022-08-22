package com.project.indytskyi.tripsservice.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class TripFinishReceiverDto which contains
 * all images after car photoshoot
 * and trafficOrderId
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TripFinishReceiverDto {

    @ApiModelProperty(example = "1", value = "")
    @Min(value = 1, message = "trafficOrderId must have correct data")
    private long trafficOrderId;

    private List<String> images;

}
