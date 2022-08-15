package com.project.indytskyi.tripsservice.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

/**
 * class TripStartDTO which contains all information
 * about new traffic order and start track
 */
@Generated
@Data
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TripStartDto {

    private long ownerId;
    private long carId;
    private long userId;
    private LocalDateTime activationTime;
    private String status;
    private String statusPaid;
    private double balance;
    private long trackId;
    private double latitude;
    private double longitude;
    private int speed;
    private LocalDateTime timestamp;
    private double distance;
    private double tariff;

}
