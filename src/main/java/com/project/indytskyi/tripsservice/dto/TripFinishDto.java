package com.project.indytskyi.tripsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * Class which contains data
 * for car and backoffice service
 * after finished order
 */
@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class TripFinishDto {
    private double latitude;
    private double longitude;
    private double tripPayment;
    private double balance;
    private long carId;
    private long userId;
    private double distance;
}
