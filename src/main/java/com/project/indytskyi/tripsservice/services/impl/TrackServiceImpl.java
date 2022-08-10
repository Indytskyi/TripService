package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TracksRepository;
import com.project.indytskyi.tripsservice.services.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


/**
 * Business logic for class Track and for Tracks Repository
 */
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final TracksRepository tracksRepository;

    private TrafficOrderEntity trafficOrderCurrent;

    @Override
    public TrackEntity createStartTrack(TrafficOrderEntity trafficOrder,
                                        TripActivationDto tripActivation) {
        TrackEntity track = initializationNewTrack(convertToCurrentCoordinates(tripActivation));
        track.setOwnerTrack(trafficOrder);
        this.trafficOrderCurrent = trafficOrder;
        return tracksRepository.save(track);
    }

    //NOT FINAL VERSION!!!!
    @Override
    public TrackEntity instanceTrack(CurrentCoordinatesDto currentCoordinates) {
        TrackEntity track = initializationNewTrack(currentCoordinates);
        track.setOwnerTrack(this.trafficOrderCurrent);
        return tracksRepository.save(track);
    }

    @Override
    public TrackEntity findOne(long id) {
        return tracksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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

    private CurrentCoordinatesDto convertToCurrentCoordinates(TripActivationDto tripActivation) {
        return new CurrentCoordinatesDto(tripActivation.getLatitude(),
                tripActivation.getLongitude());
    }

}
