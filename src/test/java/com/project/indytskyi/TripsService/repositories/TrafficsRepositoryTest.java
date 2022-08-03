package com.project.indytskyi.TripsService.repositories;

import com.project.indytskyi.TripsService.models.TrafficOrderEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TrafficsRepositoryTest {

    @Autowired
    private TrafficsRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfTrackDataBaseContainsTrafficOrderWithExistsId() {
        //GIVE
        TrafficOrderEntity trafficOrder = new TrafficOrderEntity();
        trafficOrder.setCarId(1);
        trafficOrder.setUserId(1);
        trafficOrder.setActivationTime(LocalDateTime.now());
        underTest.save(trafficOrder);
        //WHEN
        boolean expected = underTest.findById(1L).isPresent();
        //THEN
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfTrackDataBaseContainsTrafficOrderWithIdDoesNotExists() {
        //GIVE
        //WHEN
        boolean expected = underTest.findById(1L).isPresent();
        //THEN
        assertThat(expected).isFalse();
    }
}