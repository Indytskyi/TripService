package com.project.indytskyi.TripsService.services;

import com.project.indytskyi.TripsService.dto.TripActivationDTO;
import com.project.indytskyi.TripsService.models.TrafficOrderEntity;
import com.project.indytskyi.TripsService.repositories.TrafficsRepository;
import com.project.indytskyi.TripsService.util.TrafficNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TrafficOrderService {
    private final TrafficsRepository trafficsRepository;

    /**
     * save to database traffic order and first connection to order
     * @param tripActivation
     * @return TrafficOrderEntity
     */
    @Transactional
    public TrafficOrderEntity save(TripActivationDTO tripActivation) {
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
     * @param id
     * @return TrafficOrderEntity
     */
    public TrafficOrderEntity findOne(long id) {
        return trafficsRepository.findById(id).orElseThrow(TrafficNotFoundException::new);
    }


}
