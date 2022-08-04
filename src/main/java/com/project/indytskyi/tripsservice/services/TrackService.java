package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.exceptions.TrackNotFoundException;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TracksRepository;
import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Business logic for class Track and for Tracks Repository
 */
@Service
@Transactional(readOnly = true)
public class TrackService {
    private final TracksRepository tracksRepository;

    private TrafficOrderEntity trafficOrderCurrent;

    @Autowired
    public TrackService(TracksRepository tracksRepository, ModelMapper modelMapper) {
        this.tracksRepository = tracksRepository;
    }

    /**
     * Create first track in parallel with new Traffic Order and save it to Database
     */
    @Transactional
    public TrackEntity createStartTrack(TrafficOrderEntity trafficOrder,
                                        TripActivationDto tripActivation) {
        TrackEntity track = initializationNewTrack(convertToCurrentCoordinates(tripActivation));
        track.setOwnerTrack(trafficOrder);
        this.trafficOrderCurrent = trafficOrder;
        return tracksRepository.save(track);
    }

    /**
     * Create track in which we will receive constant data about the current coordinate of the car
     * and save it in
     */
    @Transactional
    // Пока не точно как будет (не знаю как решить пару моментов)
    public TrackEntity instanceTrack(CurrentCoordinatesDto currentCoordinates) {
        TrackEntity track = initializationNewTrack(currentCoordinates);
        track.setOwnerTrack(this.trafficOrderCurrent);
        return tracksRepository.save(track);
    }

    /**
     * find track by id (primary key)
     */
    public TrackEntity findOne(long id) {
        return tracksRepository.findById(id).orElseThrow(TrackNotFoundException::new);
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
