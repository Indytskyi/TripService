package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.backoffice.BackOfficeDto;

public interface BackOfficeService {

    /**
     * Get tariff from backoffice by carClass
     */
    Double getCarTariff(String carClass);

    /**
     * After finishing order, this method
     * sent information about this order
     * to back office history
     */
    void sendOrder(BackOfficeDto backOfficeDto);
}
