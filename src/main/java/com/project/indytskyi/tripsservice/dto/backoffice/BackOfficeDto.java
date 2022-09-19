package com.project.indytskyi.tripsservice.dto.backoffice;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class BackOfficeDto {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private double price;

    private int carId;

    private int userId;
}
