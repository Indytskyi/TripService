package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.AllTracksDto;
import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.exceptions.enums.StatusException;
import com.project.indytskyi.tripsservice.mapper.CurrentCoordinatesMapper;
import com.project.indytskyi.tripsservice.mapper.TrackDtoMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TracksRepository;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import com.project.indytskyi.tripsservice.util.Gfg;
import com.project.indytskyi.tripsservice.util.enums.Status;
import com.project.indytskyi.tripsservice.util.enums.TripUnits;
import com.project.indytskyi.tripsservice.validations.ServiceValidation;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Business logic for class Track and for Tracks Repository
 */
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private static final double KPH_TO_MPH = 1.60934;
    private final TracksRepository tracksRepository;
    private final CurrentCoordinatesMapper currentCoordinatesMapper;
    private final TrafficOrderService trafficOrderService;
    private final TrackDtoMapper trackDtoMapper;
    private final ServiceValidation serviceValidation;

    @Override
    public TrackEntity saveStartTrack(TrafficOrderEntity trafficOrder,
                                      TripActivationDto tripActivation) {

        TrackEntity track = initializationNewTrack(currentCoordinatesMapper
                .toCurrentCoordinates(tripActivation));
        track.setUnitOfSpeed(tripActivation.getUnitOfSpeed());
        track.setOwnerTrack(trafficOrder);
        return tracksRepository.save(track);
    }

    @Override
    public TrackEntity saveTrack(CurrentCoordinatesDto currentCoordinates) {

        final TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(currentCoordinates.getTripId());

        serviceValidation.validateStatusAccess(trafficOrder.getStatus(),
                Status.IN_ORDER.name(),
                StatusException.STOPPED_CAR_EXCEPTION.getException());

        final TrackEntity track = initializationNewTrack(currentCoordinates);
        final TrackEntity lastTrack = getLastTrack(trafficOrder);

        track.setUnitOfSpeed(lastTrack.getUnitOfSpeed());

        final double distanceBetweenTwoCoordinates =
                getDistanceBetweenTwoCoordinates(currentCoordinates, lastTrack);
        final double distance = lastTrack.getDistance()
                + distanceBetweenTwoCoordinates;

        track.setDistance(Double.parseDouble(String.format("%.2f", distance)));
        track.setOwnerTrack(trafficOrder);
        track.setSpeed(getCurrentSpeed(distanceBetweenTwoCoordinates,
                lastTrack.getTimestamp(), track.getTimestamp(), track.getUnitOfSpeed()));

        return tracksRepository.save(track);

    }

    @Override
    public TrackEntity findOne(long id) {

        return tracksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public AllTracksDto getListOfAllCoordinates(long trafficOrderId) {

        return AllTracksDto.of()
                .trafficOrderId(trafficOrderId)
                .tracks(trafficOrderService.findTrafficOrderById(trafficOrderId)
                        .getTracks()
                        .stream()
                        .map(trackDtoMapper::toTrackDto)
                        .toList())
                .build();
    }

    /**
     * create new Track and initialize it after that return it to instanceTrack or createStartTrack
     */
    private TrackEntity initializationNewTrack(CurrentCoordinatesDto currentCoordinates) {

        return TrackEntity.of()
                .latitude(currentCoordinates.getLatitude())
                .longitude(currentCoordinates.getLongitude())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Calculate distance between 2 coordinates
     */
    private double getDistanceBetweenTwoCoordinates(CurrentCoordinatesDto currentCoordinates,
                                                    TrackEntity lastTrack) {
        var distance = Gfg.distance(lastTrack.getLatitude(),
                lastTrack.getLongitude(),
                currentCoordinates.getLatitude(),
                currentCoordinates.getLongitude());
        return lastTrack.getUnitOfSpeed().equals(TripUnits.KPH.name())
                ? distance
                : distance / KPH_TO_MPH;
    }

    /**
     * Get last coordinates of car
     */
    private TrackEntity getLastTrack(TrafficOrderEntity trafficOrder) {
        return trafficOrder.getTracks().get(trafficOrder.getTracks().size() - 1);
    }

    /**
     * Calculate speed in current moment
     */
    private int getCurrentSpeed(double distance,
                                LocalDateTime previousTimestamp,
                                LocalDateTime currentTimestamp,
                                String unitOfSpeed) {

        double time = currentTimestamp.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
                - previousTimestamp.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();

        double speed = ((distance / (time)) * 3600000);
        return unitOfSpeed.equals(TripUnits.KPH.name())
                ? (int) speed
                : (int) (speed / KPH_TO_MPH);
    }

}
