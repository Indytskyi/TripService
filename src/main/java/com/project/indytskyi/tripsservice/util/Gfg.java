package com.project.indytskyi.tripsservice.util;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


/**
 * Class where you calculate distance between two coordinates
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Gfg {

    /**
     * method where you calculate distance between two coordinates
     */
    public static double distance(double previousLatitude,
                                  double previousLongitude,
                                  double currentLatitude, double currentLongitude) {

        previousLongitude = toRadians(previousLongitude);
        currentLongitude = toRadians(currentLongitude);
        previousLatitude = toRadians(previousLatitude);
        currentLatitude = toRadians(currentLatitude);

        double subtractingLongitude = currentLongitude - previousLongitude;
        double subtractingLatitude = currentLatitude - previousLatitude;
        double intermediateCalculation = pow(sin(subtractingLatitude / 2), 2)
                + cos(previousLatitude) * cos(currentLatitude)
                * pow(sin(subtractingLongitude / 2), 2);

        double arc = 2 * asin(sqrt(intermediateCalculation));

        double radiusEarth = 6371;

        return (arc * radiusEarth);
    }

}
