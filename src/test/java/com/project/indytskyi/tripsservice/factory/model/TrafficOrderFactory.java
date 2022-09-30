package com.project.indytskyi.tripsservice.factory.model;

import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.createTrack;

import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.util.Status;
import com.project.indytskyi.tripsservice.util.StatusPaid;
import java.time.LocalDateTime;
import java.util.List;

public class TrafficOrderFactory {
    public static final long TRAFFIC_ORDER_ID = 22;
    public static final LocalDateTime TRAFFIC_ORDER_ACTIVATION_TIME =
            LocalDateTime.now();
    public static final long TRAFFIC_ORDER_CAR_ID = 12;
    public static final long TRAFFIC_ORDER_USER_ID = 22;
    public static final String TRAFFIC_ORDER_STATUS = String.valueOf(Status.IN_ORDER);
    public static final String TRAFFIC_ORDER_STATUS_PAID = String.valueOf(StatusPaid.IN_PROCESS);
    public static final double TRAFFIC_ORDER_TARIFF = 300;

    public static final List<TrackEntity> TRAFFIC_ORDER_DTO_TRACKS = List.of(createTrack());

    public static TrafficOrderEntity createTrafficOrder() {
        return TrafficOrderEntity.of()
                .id(TRAFFIC_ORDER_ID)
                .carId(TRAFFIC_ORDER_CAR_ID)
                .userId(TRAFFIC_ORDER_USER_ID)
                .status(TRAFFIC_ORDER_STATUS)
                .statusPaid(TRAFFIC_ORDER_STATUS_PAID)
                .tariff(TRAFFIC_ORDER_TARIFF)
                .build();
    }

    public static TrafficOrderEntity createTrafficOrderWithTracks() {
        return TrafficOrderEntity.of()
                .id(TRAFFIC_ORDER_ID)
                .carId(TRAFFIC_ORDER_CAR_ID)
                .userId(TRAFFIC_ORDER_USER_ID)
                .status(TRAFFIC_ORDER_STATUS)
                .statusPaid(TRAFFIC_ORDER_STATUS_PAID)
                .tariff(TRAFFIC_ORDER_TARIFF)
                .tracks(TRAFFIC_ORDER_DTO_TRACKS)
                .build();
    }


}
