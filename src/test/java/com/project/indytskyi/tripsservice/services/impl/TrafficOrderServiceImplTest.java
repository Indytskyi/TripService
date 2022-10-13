package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.createTripActivationDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.createTripFinishDto;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.mapper.TrafficOrderMapper;
import com.project.indytskyi.tripsservice.mapper.TripFinishMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class TrafficOrderServiceImplTest {
    @Mock
    private TrafficsRepository trafficsRepository;

    @Mock
    private TrafficOrderMapper trafficOrderMapper;

    @Mock
    private TripFinishMapper tripFinishMapper;


    @InjectMocks
    private TrafficOrderServiceImpl underTest;


    @Test
    void canAddTrafficOrder() {
        // GIVEN
        TrafficOrderEntity orderEntity = createTrafficOrder();
        TripActivationDto tripActivation = createTripActivationDto();

        //WHEN
        when(trafficsRepository.save(any())).thenReturn(orderEntity);
        when(trafficOrderMapper.toTrafficOrderEntity(any())).thenReturn(orderEntity);

        //THEN
        TrafficOrderEntity trafficOrder = underTest.save(tripActivation);

        assertEquals(orderEntity, trafficOrder);
    }


    @Test
    void itShouldCheckIfTrafficOrderDataBaseContainsTrafficOrderWithIdExists() {
        //GIVEN
        TrafficOrderEntity orderEntity = createTrafficOrder();

        //WHEN
        when(trafficsRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

        TrafficOrderEntity expected = underTest.findTrafficOrderById(1L);
        //THEN
        assertEquals(expected, orderEntity);
    }

    @Test
    void itShouldCheckIfTrafficOrderDataBaseDoesntContainsTrafficOrderWithIdExists() {
        //GIVEN
        given(trafficsRepository.findById(1L)).willReturn(Optional.empty());

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.findTrafficOrderById(1L))
                .isInstanceOf(ResponseStatusException.class);

    }

//    @Test
//    void ifObjectOfTrafficOrderEntityChangedStatus() {
//        //GIVEN
//        TrafficOrderEntity orderEntity = createTrafficOrder();
//        String expected = "STOP";
//
//        //WHEN
//        lenient().when(trafficsRepository.findById(any())).thenReturn(Optional.of(orderEntity));
//        underTest.changeStatusOrder(1);
//
//        //THEN
//        assertEquals(expected, orderEntity.getStatus());
//    }


    @Test
    void ifObjectOfTrafficOrderEntityReturnTripFinishDto() {
        //GIVEN
        TripFinishDto expected = createTripFinishDto();
        expected.setTripPayment(25);

        TripFinishDto tripFinishDto = createTripFinishDto();

        TrafficOrderEntity orderEntity = createTrafficOrder();

        orderEntity.setActivationTime(LocalDateTime.now().minusMinutes(5));
        List<TrackEntity> tracks = new ArrayList<>();
        TrackEntity track = new TrackEntity();
        tracks.add(track);
        orderEntity.setTracks(tracks);

        //WHEN
        when(tripFinishMapper.toTripFinishDto(orderEntity, track)).thenReturn(tripFinishDto);

        //THEN
        TripFinishDto tripFinishDto1 = underTest.finishOrder(orderEntity);

        assertEquals(expected, tripFinishDto1);

    }





}