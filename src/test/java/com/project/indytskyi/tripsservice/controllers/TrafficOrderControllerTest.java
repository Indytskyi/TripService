package com.project.indytskyi.tripsservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TrafficOrderController.class)
@AllArgsConstructor
class TrafficOrderControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TrafficOrderService trafficOrderService;

    @Test
    void getTrafficOrder() {
        TrafficOrderEntity trafficOrder = new TrafficOrderEntity();
        trafficOrder.setUserId(1);
        trafficOrder.setCarId(1);
        //WHEN
        given(trafficOrderService.findOne(any())).willReturn(trafficOrder);

    }
}