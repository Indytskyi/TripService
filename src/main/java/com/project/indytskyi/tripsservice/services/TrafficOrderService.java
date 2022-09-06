package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;

public interface TrafficOrderService {

    /**
     * save to database traffic order and first connection to order
     */
    TrafficOrderEntity save(TripActivationDto tripActivation);

    /**
     * find TrafficOrder by id and if we don`t have this id (throw the exception)
     */
    TrafficOrderEntity findOne(long trafficOrderId);

    /**
     * stop order - status of orderTraffic will change to "STOP"
     * and will wait to "FINISH" status
     */
    void stopOrder(long trafficOrderId);


    /**
     * change status of order to finish after correct image
     * calculate trip payment
     * change status paid to "PAID"
     * also set completion time in order to count trip payment
     * @param  trafficOrder trafficOrder
     * @return the last {@link TrackEntity}
     */
    TripFinishDto finishOrder(TrafficOrderEntity trafficOrder);

}
