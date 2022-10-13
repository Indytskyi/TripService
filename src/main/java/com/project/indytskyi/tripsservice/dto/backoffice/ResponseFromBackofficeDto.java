package com.project.indytskyi.tripsservice.dto.backoffice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class ResponseFromBackofficeDto {
    private double ratePerHour;

}
