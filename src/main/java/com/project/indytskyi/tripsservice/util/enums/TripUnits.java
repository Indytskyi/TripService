package com.project.indytskyi.tripsservice.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TripUnits {
    MPH("miles"),
    KPH("kilometres");

    private final String unitOfDistance;
}
