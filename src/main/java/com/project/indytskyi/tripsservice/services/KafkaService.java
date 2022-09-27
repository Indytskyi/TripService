package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;

public interface KafkaService {

    void sendOrderToBackOfficeService(TrafficOrderEntity trafficOrder, double price);

    void sendOrderToCarService(TripFinishDto tripFinishDto);
}
