package com.project.indytskyi.tripsservice.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.exceptions.TrafficNotFoundException;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrafficOrderServiceTest {
    @Mock
    private TrafficsRepository trafficsRepository;

    @InjectMocks
    private TrafficOrderService underTest;


    @Test
    void canAddTrafficOrder() {
        // GIVEN
        TrafficOrderEntity orderEntity = new TrafficOrderEntity();
        orderEntity.setUserId(1);
        orderEntity.setCarId(1);

        //WHEN
        when(trafficsRepository.save(any())).thenReturn(orderEntity);


        TripActivationDto tripActivation = new TripActivationDto();
        tripActivation.setUserId(1);
        tripActivation.setCarId(1);

        //THEN
        TrafficOrderEntity trafficOrder = underTest.save(tripActivation);

        assertEquals(orderEntity, trafficOrder);
    }


    @Test
    void itShouldCheckIfTrafficOrderDataBaseContainsTrafficOrderWithIdExists() {
        //GIVEN
        TrafficOrderEntity orderEntity = new TrafficOrderEntity();
        orderEntity.setUserId(1);
        orderEntity.setCarId(1);
        //WHEN
        lenient().when(trafficsRepository.findById(any())).thenReturn(Optional.of(orderEntity));

        TrafficOrderEntity expected = new TrafficOrderEntity();
        expected.setUserId(1);
        expected.setCarId(1);

        //THEN
        assertEquals(expected, orderEntity);
    }

    @Test
    void itShouldCheckIfTrafficOrderDataBaseDoesntContainsTrafficOrderWithIdExists() {
        //GIVEN
        given(trafficsRepository.findById(1L)).willReturn(Optional.empty());
        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.findOne(1L))
                .isInstanceOf(TrafficNotFoundException.class);

    }

    @Test
    void ifObjectOfTrafficOrderEntityChangedStatus() {
        //GIVEN
        //WHEN
        TrafficOrderEntity orderEntity = new TrafficOrderEntity();
        orderEntity.setUserId(1);
        orderEntity.setCarId(1);
        String expected = "STOP";
        //WHEN
        lenient().when(trafficsRepository.findById(any())).thenReturn(Optional.of(orderEntity));
        underTest.stopOrder(1);

        assertEquals(expected, orderEntity.getStatus());
    }



}