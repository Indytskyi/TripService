package com.project.indytskyi.tripsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastCarCoordinatesDto {
    private double latitude;
    private double longitude;
    private double distance;
}
