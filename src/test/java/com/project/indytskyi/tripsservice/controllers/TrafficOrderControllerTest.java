package com.project.indytskyi.tripsservice.controllers;

import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_DISTANCE;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_ID;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_LATITUDE;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_LONGITUDE;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_SPEED;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.createTrackDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.TRIP_ACTIVATION_BALANCE;
import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.createTripActivationDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.TRIP_FINISH_DTO_CAR_ID;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.TRIP_FINISH_DTO_DISTANCE;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.TRIP_FINISH_DTO_LATITUDE;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.TRIP_FINISH_DTO_LONGITUDE;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.TRIP_FINISH_DTO_TRIP_PAYMENT;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.TRIP_FINISH_DTO_USER_ID;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.createTripFinishDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishReceiverDtoFactory.TRIP_FINISH_RECEIVER_IMAGES;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishReceiverDtoFactory.TRIP_FINISH_RECEIVER_TRAFFIC_ORDER_ID;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishReceiverDtoFactory.createTripFinishReceiverDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishReceiverDtoFactory.createTripFinishReceiverDtoInvalid;
import static com.project.indytskyi.tripsservice.factory.dto.TripStartDtoFactory.createTripStartDto;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_BALANCE;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_CAR_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_STATUS;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_STATUS_PAID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_TARIFF;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_USER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripFinishReceiverDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.mapper.StartMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.ImageService;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@AutoConfigureMockMvc
@WebMvcTest(TrafficOrderController.class)
class TrafficOrderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrafficOrderService trafficOrderService;

    @MockBean
    private TrackService trackService;

    @MockBean
    private ImageService imageService;

    @MockBean
    private StartMapper startMapper;

    @Test
    @SneakyThrows
    @DisplayName("Test finding a track by id")
    void getTrackById() {
        //GIVEN
        TrafficOrderEntity trafficOrder = createTrafficOrder();

        //WHEN
        when(trafficOrderService.findOne(TRAFFIC_ORDER_ID)).thenReturn(trafficOrder);

        mockMvc.perform(get("http://localhost:8080/trip/" + TRAFFIC_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TRAFFIC_ORDER_ID))
                .andExpect(jsonPath("$.carId").value(TRAFFIC_ORDER_CAR_ID))
                .andExpect(jsonPath("$.userId").value(TRAFFIC_ORDER_USER_ID))
                .andExpect(jsonPath("$.status").value(TRAFFIC_ORDER_STATUS))
                .andExpect(jsonPath("$.statusPaid").value(TRAFFIC_ORDER_STATUS_PAID))
                .andExpect(jsonPath("$.balance").value(TRAFFIC_ORDER_BALANCE))
                .andExpect(jsonPath("$.tariff").value(TRAFFIC_ORDER_TARIFF));

        //THEN
        verify(trafficOrderService).findOne(TRAFFIC_ORDER_ID);

    }

    @Test
    @SneakyThrows
    @DisplayName("Test finding a track by non existent id")
    void getTrackByNonExistentId() {
        //GIVEN
        when(trafficOrderService.findOne(TRAFFIC_ORDER_ID))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        //WHEN
        mockMvc.perform(get("http://localhost:8080/trip/" + TRAFFIC_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //THEN
        verify(trafficOrderService).findOne(TRAFFIC_ORDER_ID);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test saving traffic order and first coordinates of car")
    void saveTrafficOrderAndStartTrack() {
        //GIVEN
        TripActivationDto tripActivationDto = createTripActivationDto();
        TrackEntity track = createTrackDto();
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TripStartDto tripStartDto = createTripStartDto();


        //WHEN
        when(trafficOrderService.save(tripActivationDto)).thenReturn(trafficOrder);
        when(trackService.createStartTrack(trafficOrder, tripActivationDto)).thenReturn(track);
        when(startMapper.toStartDto(trafficOrder, track)).thenReturn(tripStartDto);

        mockMvc.perform(post("http://localhost:8080/trip/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripActivationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerId").value(TRAFFIC_ORDER_ID))
                .andExpect(jsonPath("$.carId").value(TRAFFIC_ORDER_CAR_ID))
                .andExpect(jsonPath("$.userId").value(TRAFFIC_ORDER_USER_ID))
                .andExpect(jsonPath("$.status").value(TRAFFIC_ORDER_STATUS))
                .andExpect(jsonPath("$.statusPaid").value(TRAFFIC_ORDER_STATUS_PAID))
                .andExpect(jsonPath("$.balance").value(TRAFFIC_ORDER_BALANCE))
                .andExpect(jsonPath("$.trackId").value(TRACK_DTO_ID))
                .andExpect(jsonPath("$.latitude").value(TRACK_DTO_LATITUDE))
                .andExpect(jsonPath("$.longitude").value(TRACK_DTO_LONGITUDE))
                .andExpect(jsonPath("$.speed").value(TRACK_DTO_SPEED))
                .andExpect(jsonPath("$.distance").value(TRACK_DTO_DISTANCE))
                .andExpect(jsonPath("$.tariff").value(TRAFFIC_ORDER_TARIFF));

        //THEN
        verify(trackService).createStartTrack(trafficOrder, tripActivationDto);
        verify(trafficOrderService).save(tripActivationDto);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test stopping traffic order ")
    void stopTrafficOrder() {
        //WHEN
        mockMvc.perform(put("http://localhost:8080/trip/stop/" + TRAFFIC_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //THEN
        verify(trafficOrderService).stopOrder(TRAFFIC_ORDER_ID);

    }

    @Test
    @SneakyThrows
    @DisplayName("Test finishing traffic order and save images")
    void finishTrafficOrder() {
        //GIVEN
        TripFinishDto tripFinishDto = createTripFinishDto();
        TripFinishReceiverDto tripFinishReceiverDto = createTripFinishReceiverDto();
        TrafficOrderEntity trafficOrder = createTrafficOrder();

        //WHEN
        when(trafficOrderService.findOne(TRIP_FINISH_RECEIVER_TRAFFIC_ORDER_ID))
                .thenReturn(trafficOrder);
        when(trafficOrderService.finishOrder(trafficOrder)).thenReturn(tripFinishDto);

        mockMvc.perform(put("http://localhost:8080/trip/finish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripFinishReceiverDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(TRIP_FINISH_DTO_LATITUDE))
                .andExpect(jsonPath("$.longitude").value(TRIP_FINISH_DTO_LONGITUDE))
                .andExpect(jsonPath("$.tripPayment").value(TRIP_FINISH_DTO_TRIP_PAYMENT))
                .andExpect(jsonPath("$.balance").value(TRIP_ACTIVATION_BALANCE))
                .andExpect(jsonPath("$.carId").value(TRIP_FINISH_DTO_CAR_ID))
                .andExpect(jsonPath("$.userId").value(TRIP_FINISH_DTO_USER_ID))
                .andExpect(jsonPath("$.distance").value(TRIP_FINISH_DTO_DISTANCE));

        //THEN
        verify(imageService).saveImages(trafficOrder, TRIP_FINISH_RECEIVER_IMAGES);
        verify(trafficOrderService).finishOrder(trafficOrder);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test finishing traffic order with invalid traffic order id")
    void finishTrafficOrderWithInvalidTrafficOrderId() {
        //GIVEN
        TripFinishReceiverDto tripFinishReceiverDto = createTripFinishReceiverDtoInvalid();

        //WHEN
        mockMvc.perform(put("http://localhost:8080/trip/finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripFinishReceiverDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field").value("trafficOrderId"))
                .andExpect(jsonPath("$..message").
                        value("trafficOrderId must have correct data"));
    }

}