package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.exceptions.TrafficNotFoundException;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TrafficOrderServiceImpl implements TrafficOrderService {
    private final TrafficsRepository trafficsRepository;

    @Override
    public TrafficOrderEntity save(TripActivationDto tripActivation) {
        TrafficOrderEntity trafficOrder = new TrafficOrderEntity();
        trafficOrder.setUserId(tripActivation.getUserId());
        trafficOrder.setCarId(tripActivation.getCarId());
        trafficOrder.setActivationTime(LocalDateTime.now());
        trafficOrder.setBalance(tripActivation.getBalance());
        trafficOrder.setStatus("IN ORDER");
        trafficOrder.setStatusPaid("IN PROCESS");
        return trafficsRepository.save(trafficOrder);
    }

    @Override
    public TrafficOrderEntity findOne(long id) {
        return trafficsRepository.findById(id).orElseThrow(TrafficNotFoundException::new);
    }

    @Override
    public void stopOrder(long id) {
        findOne(id).setStatus("STOP");
    }

}
