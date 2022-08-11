package com.project.indytskyi.tripsservice.dto;

import lombok.Data;

/**
 * Class which contains data
 * for car and backoffice service
 * after finished order
 */
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
