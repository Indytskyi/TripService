package com.project.indytskyi.tripsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LastCarCoordinatesDto {
    private double latitude;
    private double longitude;
    private double distance;

}
