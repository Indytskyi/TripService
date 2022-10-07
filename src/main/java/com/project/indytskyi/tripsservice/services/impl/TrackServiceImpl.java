package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.AllTracksDto;
import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.mapper.CurrentCoordinatesMapper;
import com.project.indytskyi.tripsservice.mapper.TrackDtoMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TracksRepository;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import com.project.indytskyi.tripsservice.services.UserService;
import com.project.indytskyi.tripsservice.util.Gfg;
import com.project.indytskyi.tripsservice.util.enums.Status;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


/**
 * Business logic for class Track and for Tracks Repository
 */
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private final TracksRepository tracksRepository;
    private final CurrentCoordinatesMapper currentCoordinatesMapper;

    private final TrafficOrderService trafficOrderService;

    private final TrackDtoMapper trackDtoMapper;

    private final UserService userService;

    @Override
    public TrackEntity saveStartTrack(TrafficOrderEntity trafficOrder,
                                      TripActivationDto tripActivation) {

        TrackEntity track = initializationNewTrack(currentCoordinatesMapper
                .toCurrentCoordinates(tripActivation));
        track.setOwnerTrack(trafficOrder);
        return tracksRepository.save(track);
    }

    @Transactional
    @Override
    public TrackEntity saveTrack(CurrentCoordinatesDto currentCoordinates, String token) {

        userService.checkIfTheConsumerIsUser(token);

        final TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(currentCoordinates.getTripId());

        if (!trafficOrder.getStatus().equals(Status.IN_ORDER.name())) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    "The machine has stopped, take the machine off pause")));
        }
        final TrackEntity track = initializationNewTrack(currentCoordinates);
        final TrackEntity lastTrack = getLastTrack(trafficOrder);
        final double distanceBetweenTwoCoordinates =
                getDistanceBetweenTwoCoordinates(currentCoordinates, lastTrack);
        final double distance = lastTrack.getDistance()
                + distanceBetweenTwoCoordinates;
        track.setDistance(distance);
        track.setOwnerTrack(trafficOrder);
        track.setSpeed(getCurrentSpeed(distanceBetweenTwoCoordinates,
                lastTrack.getTimestamp(), track.getTimestamp()));
        return tracksRepository.save(track);
    }

    @Override
    public TrackEntity findOne(long id, String token) {

        userService.checkIfTheConsumerIsAdmin(token);

        return tracksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public AllTracksDto getListOfAllCoordinates(long id, String token) {

        userService.checkIfTheConsumerIsUser(token);

        return AllTracksDto.of()
                .trafficOrderId(id)
                .tracks(trafficOrderService.findTrafficOrderById(id)
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
        TrackEntity track = new TrackEntity();
        track.setLatitude(currentCoordinates.getLatitude());
        track.setLongitude(currentCoordinates.getLongitude());
        track.setTimestamp(LocalDateTime.now());
        return track;
    }

    /**
     * Calculate distance between 2 coordinates
     */
    private double getDistanceBetweenTwoCoordinates(CurrentCoordinatesDto currentCoordinates,
                                                    TrackEntity lastTrack) {
        return Gfg.distance(lastTrack.getLatitude(),
                lastTrack.getLongitude(),
                currentCoordinates.getLatitude(),
                currentCoordinates.getLongitude());
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
                                LocalDateTime currentTimestamp) {
        double time = currentTimestamp.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
                - previousTimestamp.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        ;
        return (int) ((distance / (time)) * 3600000);
    }

}
