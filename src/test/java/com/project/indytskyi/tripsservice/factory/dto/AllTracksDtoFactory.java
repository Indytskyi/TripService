package com.project.indytskyi.tripsservice.factory.dto;

import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.createTrackDto;

import com.project.indytskyi.tripsservice.dto.AllTracksDto;
import com.project.indytskyi.tripsservice.dto.TrackDto;
import java.util.List;

public class AllTracksDtoFactory {

    public static final long ALL_TRACKS_ORDER_ID = 22;

    public static final List<TrackDto> TRAFFIC_ORDER_DTO_TRACKS =
            List.of(createTrackDto());

    public static AllTracksDto createAllTracksDto() {
        return AllTracksDto.of()
                .trafficOrderId(ALL_TRACKS_ORDER_ID)
                .tracks(TRAFFIC_ORDER_DTO_TRACKS)
                .build();
    }

}
