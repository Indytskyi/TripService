package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.mapper.TrafficOrderMapper;
import com.project.indytskyi.tripsservice.mapper.TripFinishMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import com.project.indytskyi.tripsservice.util.enums.Status;
import com.project.indytskyi.tripsservice.util.enums.StatusPaid;
import com.project.indytskyi.tripsservice.validations.TrafficOrderValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    public TrafficOrderEntity findTrafficOrderById(long id) {
        return trafficsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    // TODO remove  @Transactional
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
     *
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
