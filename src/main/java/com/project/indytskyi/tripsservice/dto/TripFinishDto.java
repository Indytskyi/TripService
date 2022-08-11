package com.project.indytskyi.tripsservice.dto;

import lombok.Data;

@Data
public class TripFinishDto {
    private double latitude;
    private double longitude;
    private double tripPayment;
    private double balance;
    private long carId;
    private long userId;
    private double distance;
}
