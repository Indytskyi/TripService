package com.project.indytskyi.tripsservice.factory.dto;

import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.util.enums.Status;
import java.time.LocalDateTime;

public class TrafficOrderDtoFactory {
    public static final long TRAFFIC_ORDER_DTO_ID = 22;
    public static final LocalDateTime TRAFFIC_ORDER_ACTIVATION_TIME =
            LocalDateTime.now();
    public static final long TRAFFIC_ORDER_DTO_CAR_ID = 12;
    public static final long TRAFFIC_ORDER_DTO_USER_ID = 22;
    public static final String TRAFFIC_ORDER_DTO_STATUS = String.valueOf(Status.IN_ORDER);
    public static final double TRAFFIC_ORDER_DTO_TARIFF = 300;


    public static TrafficOrderDto createTrafficOrderDto() {
        return TrafficOrderDto.of()
                .id(TRAFFIC_ORDER_DTO_ID)
                .carId(TRAFFIC_ORDER_DTO_CAR_ID)
                .userId(TRAFFIC_ORDER_DTO_USER_ID)
                .status(TRAFFIC_ORDER_DTO_STATUS)
                .tariff(TRAFFIC_ORDER_DTO_TARIFF)
                .build();
    }
}
