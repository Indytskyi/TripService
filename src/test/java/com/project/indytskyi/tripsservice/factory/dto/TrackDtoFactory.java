package com.project.indytskyi.tripsservice.factory.dto;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;

import com.project.indytskyi.tripsservice.dto.TrackDto;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import java.time.LocalDateTime;

public class TrackDtoFactory {
    public static final long TRACK_DTO_ID = 27L;
    public static final double TRACK_DTO_DISTANCE = 0;
    public static final double TRACK_DTO_LATITUDE = 4; //4.5
    public static final double TRACK_DTO_LONGITUDE = 3; // 3.5
    public static final int TRACK_DTO_SPEED = 0;
    public static final LocalDateTime TRACK_DTO_TIMESTAMP = LocalDateTime.now();
    public static final TrafficOrderEntity TRACK_DTO_TRAFFIC_ORDER = createTrafficOrder();

    public static TrackDto createTrackDto() {
        return TrackDto.of()
                .id(TRACK_DTO_ID)
                .distance(TRACK_DTO_DISTANCE)
                .latitude(TRACK_DTO_LATITUDE)
                .longitude(TRACK_DTO_LONGITUDE)
                .speed(TRACK_DTO_SPEED)
                .timestamp(TRACK_DTO_TIMESTAMP)
                .ownerTrack(TRACK_DTO_TRAFFIC_ORDER)
                .build();
    }
}
