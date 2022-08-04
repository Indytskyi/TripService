package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.exceptions.TrafficNotFoundException;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TrafficOrderService {
    private final TrafficsRepository trafficsRepository;

    /**
     * save to database traffic order and first connection to order
     */
    @Transactional
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

    /**
     * find TrafficOrder by id and if we don`t have this id (throw the exception)
     */
    public TrafficOrderEntity findOne(long id) {
        return trafficsRepository.findById(id).orElseThrow(TrafficNotFoundException::new);
    }

    @Transactional
    public void stopOrder(long id) {
        findOne(id).setStatus("STOP");
    }

}
