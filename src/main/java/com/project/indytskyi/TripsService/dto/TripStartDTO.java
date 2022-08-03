package com.project.indytskyi.TripsService.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * class TripStartDTO which contains all information
 * about new traffic order and start track
 */
@Data
public class TripStartDTO {
    private long ownerId;
    private long carId;
    private long userId;
    private LocalDateTime activationTime;
    private String status;
    private String statusPaid;
    private Double balance;
    private long trackId;
    private double latitude;
    private double longitude;
    private int speed;
    private LocalDateTime timestamp;
    private double distance;
}
