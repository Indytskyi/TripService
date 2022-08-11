package com.project.indytskyi.tripsservice.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Class TripFinishReceiverDto which contains
 * all images after car photoshoot
 * and trafficOrderId
 */
@Data
public class TripFinishReceiverDto {
    @Min(value = 1, message = "trafficOrderId must have correct data")
    private long trafficOrderId;
    private List<String> images;
}
