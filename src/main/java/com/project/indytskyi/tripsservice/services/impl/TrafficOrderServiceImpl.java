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
import com.project.indytskyi.tripsservice.util.enums.TripUnits;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TrafficOrderServiceImpl implements TrafficOrderService {

    private final TrafficsRepository trafficsRepository;
    private final TrafficOrderMapper trafficOrderMapper;
    private final TripFinishMapper tripFinishMapper;

    @Override
    public TrafficOrderEntity save(TripActivationDto tripActivation) {

        TrafficOrderEntity trafficOrder = trafficOrderMapper
                .toTrafficOrderEntity(tripActivation);
        trafficOrder.setActivationTime(LocalDateTime.now());
        trafficOrder.setStatus(String.valueOf(Status.IN_ORDER));
        return trafficsRepository.save(trafficOrder);
    }

    @Override
    public TrafficOrderEntity findTrafficOrderById(long id) {

        return trafficsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    // TODO remove  @Transactional
    @Override
    public TripFinishDto finishOrder(TrafficOrderEntity trafficOrder) {

        trafficOrder.setCompletionTime(LocalDateTime.now());
        trafficOrder.setStatus(String.valueOf(Status.FINISH));
        TrackEntity track = trafficOrder.getTracks().get(trafficOrder.getTracks().size() - 1);

        TripFinishDto tripFinishDto = tripFinishMapper
                .toTripFinishDto(trafficOrder, track);

        tripFinishDto.setUnitOfDistance(track.getUnitOfSpeed().equals(TripUnits.KPH.name())
                ? TripUnits.KPH.getUnitOfDistance()
                : TripUnits.MPH.getUnitOfDistance());
        tripFinishDto.setTripPayment(Double.parseDouble(String
                .format("%.2f", calculateTripPayment(trafficOrder))));

        trafficsRepository.save(trafficOrder);

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
