package com.project.indytskyi.TripsService.services;

import com.project.indytskyi.TripsService.dto.CurrentCoordinatesDTO;
import com.project.indytskyi.TripsService.dto.TripActivationDTO;
import com.project.indytskyi.TripsService.models.TrackEntity;
import com.project.indytskyi.TripsService.models.TrafficOrderEntity;
import com.project.indytskyi.TripsService.repositories.TracksRepository;
import com.project.indytskyi.TripsService.util.TrackNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
     * @param trafficOrder
     * @param tripActivation
     * @return TrackEntity
     */
    @Transactional
    public TrackEntity createStartTrack(TrafficOrderEntity trafficOrder, TripActivationDTO tripActivation) {
        TrackEntity track = initializationNewTrack(convertToCurrentCoordinates(tripActivation));
        track.setOwnerTrack(trafficOrder);
        this.trafficOrderCurrent = trafficOrder;
         return tracksRepository.save(track);
    }


    /**
     * Create track in which we will receive constant data about the current coordinate of the car
     * and save it in
     * @param currentCoordinates
     * @return TrackEntity
     */
    @Transactional
    // Пока не точно как будет (не знаю как решить пару моментов)
    public TrackEntity instanceTrack(CurrentCoordinatesDTO currentCoordinates) {
        TrackEntity track = initializationNewTrack(currentCoordinates);
        track.setOwnerTrack(this.trafficOrderCurrent);
        return tracksRepository.save(track);
    }


    /**
     * find track by id (primary key)
     * @param id
     * @return
     */
    public TrackEntity findOne(long id) {
        return tracksRepository.findById(id).orElseThrow(TrackNotFoundException::new);
    }


    /**
     * create new Track and initialize it after that return it to instanceTrack or createStartTrack
     * @param currentCoordinates
     * @return TrackEntity
     */
    private TrackEntity initializationNewTrack(CurrentCoordinatesDTO currentCoordinates) {
        TrackEntity track = new TrackEntity();
        track.setLatitude(currentCoordinates.getLatitude());
        track.setLongitude(currentCoordinates.getLongitude());
        track.setTimestamp(LocalDateTime.now());
        return track;
    }


    private CurrentCoordinatesDTO convertToCurrentCoordinates(TripActivationDTO tripActivation) {
        return new CurrentCoordinatesDTO(tripActivation.getLatitude(), tripActivation.getLongitude());
    }



}
