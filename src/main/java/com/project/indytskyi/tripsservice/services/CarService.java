package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;

public interface CarService {

    /**
     * Get from microservice car-service information
     * about car by carId
     * set to tripActivation latitude and longitude
     * return carClass for request to backoffice-service
     * to get a tariff
     */
    CarDto getCarInfo(TripActivationDto tripActivationDto, String token);

    /**
     * the method that cals put request in car-service
     * and set the new CarStatus for car
     * from FREE to RENTED
     */
    void setCarStatus(long carId, String token);

}
