package com.project.indytskyi.TripsService.controllers;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TrafficOrderController.class)
@AllArgsConstructor
class TrafficOrderControllerTest {


    private MockMvc mvc;



    @Test
    void getTrafficOrder() {

    }
}