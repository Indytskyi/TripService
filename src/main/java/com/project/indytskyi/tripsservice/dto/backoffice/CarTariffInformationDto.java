package com.project.indytskyi.tripsservice.dto.backoffice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class CarTariffInformationDto {
    private long id;
    private double ratePerHour;
    private String currency;
    private String unitOfSpeed;
}
