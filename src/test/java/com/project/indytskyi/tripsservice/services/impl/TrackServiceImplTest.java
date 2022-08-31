package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.dto.CurrentCoordinatesDtoFactory.createCurrentCoordinatesDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.createTripActivationDto;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.createTrack;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.mapper.CurrentCoordinatesMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TracksRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {
    @Mock
    private TracksRepository tracksRepository;

    @Mock
    private CurrentCoordinatesMapper currentCoordinatesMapper;


    @InjectMocks
    private TrackServiceImpl underTest;

    @Test
    void canAddStartTrack() {
        //GIVEN
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TrackEntity track  = createTrack();
        TripActivationDto tripActivation = createTripActivationDto();
        CurrentCoordinatesDto coordinatesDto = createCurrentCoordinatesDto();
        //WHEN
        when(tracksRepository.save(any())).thenReturn(track);
        when(currentCoordinatesMapper
                .toCurrentCoordinates(tripActivation)).thenReturn(coordinatesDto);
        //THEN
        TrackEntity expected = underTest.createStartTrack(trafficOrder, tripActivation);

        assertEquals(expected, track);
    }

    @Test
    void instanceTrack() {
        //GIVEN
        TrackEntity track = createTrack();
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        CurrentCoordinatesDto coordinatesDTO = createCurrentCoordinatesDto();
        trafficOrder.setTracks(List.of(track));
        //WHEN
        when(tracksRepository.save(any())).thenReturn(track);


        //THEN
        TrackEntity expected = underTest.instanceTrack(coordinatesDTO, trafficOrder);

        assertEquals(expected, track);
    }

    @Test
    void itShouldCheckIfTrackDataBaseContainsTrackWithIdExists() {
        //GIVEN
        TrackEntity trackEntity = createTrack();

        //WHEN
        when(tracksRepository.findById(1L)).thenReturn(Optional.of(trackEntity));
        TrackEntity expected = underTest.findOne(1L);

        //THEN

        assertThat(expected).isEqualTo(trackEntity);
    }

    @Test
    void itShouldCheckIfTrackDataBaseDoesntContainsTrackWithIdExists() {
        //GIVEN
        given(tracksRepository.findById(1L)).willReturn(Optional.empty());

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.findOne(1L))
                .isInstanceOf(ResponseStatusException.class);

    }

}