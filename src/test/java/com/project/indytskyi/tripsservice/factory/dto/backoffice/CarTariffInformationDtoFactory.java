package com.project.indytskyi.tripsservice.factory.dto.backoffice;

import com.project.indytskyi.tripsservice.dto.backoffice.CarTariffInformationDto;

public class CarTariffInformationDtoFactory {
    private static final double CAR_TARIFF_RATE_PER_HOUR = 200;
    private static final String CAR_TARIFF_CURRENCY = "USD";

    public static CarTariffInformationDto createCARTariffInformationDto() {
        return CarTariffInformationDto.of()
                .ratePerHour(CAR_TARIFF_RATE_PER_HOUR)
                .currency(CAR_TARIFF_CURRENCY)
                .build();
    }
}
