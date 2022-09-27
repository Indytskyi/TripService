package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.mapper.TrafficOrderMapper;
import com.project.indytskyi.tripsservice.mapper.TripFinishMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import com.project.indytskyi.tripsservice.util.Status;
import com.project.indytskyi.tripsservice.util.StatusPaid;
import com.project.indytskyi.tripsservice.validations.TrafficOrderValidation;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TrafficOrderServiceImpl implements TrafficOrderService {

    private final TrafficsRepository trafficsRepository;
    private final TrafficOrderMapper trafficOrderMapper;
    private final TripFinishMapper tripFinishMapper;

    private final TrafficOrderValidation trafficOrderValidation;

    @Override
    public TrafficOrderEntity save(TripActivationDto tripActivation) {
        trafficOrderValidation
                .validateActiveCountOfTrafficOrders(tripActivation.getUserId());

        TrafficOrderEntity trafficOrder = trafficOrderMapper
                .toTrafficOrderEntity(tripActivation);
        trafficOrder.setActivationTime(LocalDateTime.now());
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
    public void stopOrder(long trafficOrderId) {
        findOne(trafficOrderId).setStatus(String.valueOf(Status.STOP));
    }

    @Transactional
    @Override
    public TripFinishDto finishOrder(TrafficOrderEntity trafficOrder) {

        trafficOrder.setCompletionTime(LocalDateTime.now());
        trafficOrder.setStatus(String.valueOf(Status.FINISH));
        trafficOrder.setStatusPaid(String.valueOf(StatusPaid.PAID));
        TrackEntity track = trafficOrder.getTracks().get(trafficOrder.getTracks().size() - 1);

        TripFinishDto tripFinishDto = tripFinishMapper
                .toTripFinishDto(trafficOrder, track);

        tripFinishDto.setTripPayment(calculateTripPayment(trafficOrder));

        return tripFinishDto;

    }

    /**
     * calculate trip payment
     * @return trip payment = {@link Double}
     */
    private double calculateTripPayment(TrafficOrderEntity trafficOrder) {
        final double minutesInHour = 60;
        final double pricePerMinute = trafficOrder.getTariff() / minutesInHour;

        long travelTimeInMinutes = ChronoUnit.MINUTES.between(
                trafficOrder.getActivationTime(),
                trafficOrder.getCompletionTime()
        );

        return travelTimeInMinutes * pricePerMinute;
    }

}
