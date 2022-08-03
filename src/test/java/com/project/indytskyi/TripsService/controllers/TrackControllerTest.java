package com.project.indytskyi.TripsService.controllers;

import com.project.indytskyi.TripsService.services.TrackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


class TrackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackService trackService;

    @Test
    void getTrackOrder() {


    }

    @Test
    void getCurrentCoordinates() {
    }
}