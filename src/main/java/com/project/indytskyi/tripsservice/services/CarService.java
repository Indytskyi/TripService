package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;

public interface CarService {

    /**
     * Get from microservice car-service information
     * about car by carId
     * set to tripActivation latitude and longitude
     * return carClass for request to backoffice-service
     * to get a tariff
     */
    String getCarInfo(TripActivationDto tripActivationDto);

    /**
     * the method that cals put request in car-service
     * and set the new CarStatus for car
     * from FREE to RENTED
     */
    void setCarStatus(long carId);

    /**
     * the method that cals put request in car-service
     * and set in car the last coordinates of car after trip,
     * distance, fuel level and status of car
     */
    void setCarAfterFinishingOrder(TripFinishDto tripFinishDto, long carId);

}
