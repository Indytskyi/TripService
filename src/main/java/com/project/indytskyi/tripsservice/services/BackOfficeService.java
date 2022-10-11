package com.project.indytskyi.tripsservice.services;

public interface BackOfficeService {

    /**
     * Get tariff from backoffice by carClass
     */
    Double getCarTariff(String carClass, String token);

}
