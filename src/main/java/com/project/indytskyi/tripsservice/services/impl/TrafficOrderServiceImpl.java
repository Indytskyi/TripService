package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import com.project.indytskyi.tripsservice.util.Status;
import com.project.indytskyi.tripsservice.util.StatusPaid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        trafficOrder.setStatus(String.valueOf(Status.IN_ORDER));
        trafficOrder.setStatusPaid(String.valueOf(StatusPaid.IN_PROCESS));
        return trafficsRepository.save(trafficOrder);
    }

    @Override
    public TrafficOrderEntity findOne(long id) {
        return trafficsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public void stopOrder(long id) {
        findOne(id).setStatus(String.valueOf(Status.STOP));
    }

}
