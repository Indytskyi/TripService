package com.project.indytskyi.tripsservice.factory.model;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;

import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import java.time.LocalDateTime;

public class TrackFactory {
    public static final long TRACK_ID = 27L;
    public static final double TRACK_DISTANCE = 0;
    public static final double TRACK_LATITUDE = 4; //4.5
    public static final double TRACK_LONGITUDE = 3; // 3.5
    public static final int TRACK_SPEED = 0;
    public static final LocalDateTime TRACK_TIMESTAMP = LocalDateTime.now();
    public static final TrafficOrderEntity TRACK_TRAFFIC_ORDER = createTrafficOrder();

    public static TrackEntity createTrack() {
        return TrackEntity.of()
                .id(TRACK_ID)
                .distance(TRACK_DISTANCE)
                .latitude(TRACK_LATITUDE)
                .longitude(TRACK_LONGITUDE)
                .speed(TRACK_SPEED)
                .timestamp(TRACK_TIMESTAMP)
                .ownerTrack(TRACK_TRAFFIC_ORDER)
                .build();
    }
}
