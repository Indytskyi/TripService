package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.backoffice.CarTariffInformationDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;

public interface BackOfficeService {

    /**
     * Get tariff from backoffice by carClass
     */
    CarTariffInformationDto getCarTariffResponse(CarDto carDto, String token);

}
