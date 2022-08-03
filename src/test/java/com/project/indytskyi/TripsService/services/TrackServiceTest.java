package com.project.indytskyi.TripsService.services;

import com.project.indytskyi.TripsService.dto.CurrentCoordinatesDTO;
import com.project.indytskyi.TripsService.dto.TripActivationDTO;
import com.project.indytskyi.TripsService.models.TrackEntity;
import com.project.indytskyi.TripsService.models.TrafficOrderEntity;
import com.project.indytskyi.TripsService.repositories.TracksRepository;
import com.project.indytskyi.TripsService.util.TrackNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {
    @Mock
    private TracksRepository tracksRepository;



    @InjectMocks
    private TrackService underTest;


    @Test
    void canAddStartTrack() {
        //GIVEN
        TrafficOrderEntity trafficOrder = new TrafficOrderEntity();
        TrackEntity track = new TrackEntity();
        track.setLatitude(10);
        track.setLongitude(1);
        track.setOwnerTrack(trafficOrder);
        when(tracksRepository.save(any())).thenReturn(track);

        //GIVEN
        TripActivationDTO tripActivation = new TripActivationDTO();
        tripActivation.setLatitude(10);
        tripActivation.setLongitude(1);

        TrackEntity expected = underTest.createStartTrack(trafficOrder, tripActivation);

        assertEquals(expected, track);
    }

    @Test
    void instanceTrack() {
        //GIVEN
        TrackEntity track = new TrackEntity();
        track.setLatitude(1);
        track.setLongitude(1);

        //WHEN
        when(tracksRepository.save(any())).thenReturn(track);

        CurrentCoordinatesDTO coordinatesDTO = new CurrentCoordinatesDTO();
        coordinatesDTO.setLongitude(1);
        coordinatesDTO.setLatitude(1);

        //THEN
        TrackEntity expected = underTest.instanceTrack(coordinatesDTO);

        assertEquals(expected, track);
    }


    @Test
    void itShouldCheckIfTrackDataBaseContainsTrackWithIdExists() {
        //GIVEN
        TrackEntity trackEntity = new TrackEntity();

        //WHEN
        lenient().when(tracksRepository.findById(any())).thenReturn(Optional.of(trackEntity));
        TrackEntity expected = new TrackEntity();

        //THEN
        assertEquals(expected, trackEntity);
    }

    @Test
    void itShouldCheckIfTrackDataBaseDoesntContainsTrackWithIdExists() {
        //GIVEN
        given(tracksRepository.findById(1L)).willReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.findOne(1L))
                .isInstanceOf(TrackNotFoundException.class);
    }


}