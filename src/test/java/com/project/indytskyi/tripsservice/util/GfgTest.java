package com.project.indytskyi.tripsservice.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GfgTest {

    @Test
    void distance() {

        double expected = 157.01016264060183;

        double previousLatitude = 5;
        double previousLongitude = 4;
        double currentLatitude = 4;
        double currentLongitude = 3;

        double distance = Gfg.distance(previousLatitude,
                previousLongitude,
                currentLatitude,
                currentLongitude);

        assertEquals(expected, distance);
    }
}
