package com.project.indytskyi.tripsservice.factory.dto;

import java.util.List;

public class TripFinishReceiverDtoFactory {

    public static final long TRIP_FINISH_RECEIVER_TRAFFIC_ORDER_ID = 22;
    public static final long TRIP_FINISH_RECEIVER_TRAFFIC_ORDER_ID_INVALID = -22;
    public static final List<String> TRIP_FINISH_RECEIVER_IMAGES =
            List.of("drive.google.com/drive/folders/1.jpg",
                    "drive.google.com/drive/folders/2.jpg",
                    "drive.google.com/drive/folders/3.jpg");

    public static TripFinishReceiverDto createTripFinishReceiverDto() {
        return TripFinishReceiverDto.of()
                .trafficOrderId(TRIP_FINISH_RECEIVER_TRAFFIC_ORDER_ID)
                .images(TRIP_FINISH_RECEIVER_IMAGES)
                .build();
    }

    public static TripFinishReceiverDto createTripFinishReceiverDtoInvalid() {
        return TripFinishReceiverDto.of()
                .trafficOrderId(TRIP_FINISH_RECEIVER_TRAFFIC_ORDER_ID_INVALID)
                .images(TRIP_FINISH_RECEIVER_IMAGES)
                .build();
    }

}
