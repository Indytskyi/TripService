package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.exceptions.TrackNotFoundException;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TracksRepository;
import com.project.indytskyi.tripsservice.services.impl.TrackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {
    @Mock
    private TracksRepository tracksRepository;

    @InjectMocks
    private TrackServiceImpl underTest;

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
        TripActivationDto tripActivation = new TripActivationDto();
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

        CurrentCoordinatesDto coordinatesDTO = new CurrentCoordinatesDto();
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