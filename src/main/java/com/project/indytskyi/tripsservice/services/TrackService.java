package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;

public interface TrackService {

    /**
     * Create first track in parallel with new Traffic Order and save it to Database
     */
    TrackEntity saveStartTrack(TrafficOrderEntity trafficOrder,
                                 TripActivationDto tripActivation);

    /**
     * Create track in which we will receive constant data about the current coordinate of the car
     * and save it in
     */
    TrackEntity saveTrack(CurrentCoordinatesDto coordinatesDto,
                              TrafficOrderEntity trafficOrder);

    /**
     * find track by id (primary key)
     */
    TrackEntity findOne(long id);

}
