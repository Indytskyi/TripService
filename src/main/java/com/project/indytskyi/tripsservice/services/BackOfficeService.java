package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.car.CarDto;

public interface BackOfficeService {

    /**
     * Get tariff from backoffice by carClass
     */
    double getCarTariff(CarDto carDto, String token);

}
