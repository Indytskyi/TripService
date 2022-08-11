package com.project.indytskyi.tripsservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * class TripStartDTO which contains all information
 * about new traffic order and start track
 */
@Data
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
