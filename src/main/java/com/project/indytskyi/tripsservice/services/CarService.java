package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;

public interface CarService {

    String getCarInfo(TripActivationDto tripActivationDto);

    void setCarStatus(long carId);

    void setCarAfterFinishingOrder(TripFinishDto tripFinishDto, long carId);

}
