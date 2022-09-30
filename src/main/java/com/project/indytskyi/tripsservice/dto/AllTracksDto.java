package com.project.indytskyi.tripsservice.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true, builderMethodName = "of")
public class AllTracksDto {
    private long trafficOrderId;
    private List<TrackDto> tracks;
}
