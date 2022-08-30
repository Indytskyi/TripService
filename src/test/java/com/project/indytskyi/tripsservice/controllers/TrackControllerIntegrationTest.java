package com.project.indytskyi.tripsservice.controllers;

import static com.project.indytskyi.tripsservice.factory.dto.CurrentCoordinatesDtoFactory.createCurrentCoordinatesDto;
import static com.project.indytskyi.tripsservice.factory.dto.CurrentCoordinatesDtoFactory.currentCoordinatesDtoForSavingWithInvalidLatitude;
import static com.project.indytskyi.tripsservice.factory.dto.CurrentCoordinatesDtoFactory.currentCoordinatesDtoForSavingWithInvalidLongitude;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.createTrackDto;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.TRACK_DISTANCE;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.TRACK_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.TRACK_LATITUDE;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.TRACK_LONGITUDE;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.TRACK_SPEED;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.createTrack;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TrackDto;
import com.project.indytskyi.tripsservice.mapper.TrackDtoMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import java.util.List;
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
@WebMvcTest(TrackController.class)
//@Testcontainers
class TrackControllerIntegrationTest {

//    @Container
//    public static PostgreSQLContainer container =
//            (PostgreSQLContainer) new PostgreSQLContainer("postgres:14-alpine")
//                    .withExposedPorts(8080);
//
//
//    @DynamicPropertySource
//    public static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url",container::getJdbcUrl);
//        registry.add("spring.datasource.username", container::getUsername);
//        registry.add("spring.datasource.password", container::getPassword);
//
//    }
//
//    @BeforeAll
//    static void beforeAll() {
//        container.start();
//    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrackService trackService;

    @MockBean
    private TrafficOrderService trafficOrderService;

    @MockBean
    private TrackDtoMapper trackDtoMapper;

    @Test
    @SneakyThrows
    @DisplayName("Test finding a track by id")
    void getTrackById() {
        //GIVEN
        TrackEntity track = createTrack();
        TrackDto trackDto = createTrackDto();

        //WHEN
        when(trackService.findOne(TRACK_ID)).thenReturn(track);
        when(trackDtoMapper.toTrackDto(track)).thenReturn(trackDto);

        mockMvc.perform(get("http://localhost:8080/trip/track/" + TRACK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TRACK_ID))
                .andExpect(jsonPath("$.latitude").value(TRACK_LATITUDE))
                .andExpect(jsonPath("$.longitude").value(TRACK_LONGITUDE))
                .andExpect(jsonPath("$.speed").value(TRACK_SPEED))
                .andExpect(jsonPath("$.distance").value(TRACK_DISTANCE));

        //THEN
        verify(trackService).findOne(TRACK_ID);

    }

    @Test
    @SneakyThrows
    @DisplayName("Test finding a track by non existent id")
    void getTrackByNonExistentId() {
        //GIVEN
        when(trackService.findOne(TRACK_ID))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        //WHEN
        mockMvc.perform(get("http://localhost:8080/trip/track/" + TRACK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //THEN
        verify(trackService).findOne(TRACK_ID);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test adding current coordinates ")
    void addCurrentCoordinates() {
        //GIVEN
        CurrentCoordinatesDto coordinatesDto = createCurrentCoordinatesDto();
        TrackEntity track = createTrack();
        TrackDto trackDto = createTrackDto();
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        trafficOrder.setTracks(List.of(track));


        //WHEN
        when(trackService.instanceTrack(coordinatesDto, trafficOrder)).thenReturn(track);
        when(trackDtoMapper.toTrackDto(track)).thenReturn(trackDto);
        when(trafficOrderService.findOne(coordinatesDto.getTrafficOrderId())).thenReturn(trafficOrder);

        mockMvc.perform(post("http://localhost:8080/trip/track")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coordinatesDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TRACK_ID))
                .andExpect(jsonPath("$.latitude").value(TRACK_LATITUDE))
                .andExpect(jsonPath("$.longitude").value(TRACK_LONGITUDE))
                .andExpect(jsonPath("$.speed").value(TRACK_SPEED))
                .andExpect(jsonPath("$.distance").value(TRACK_DISTANCE));

        //THEN
        verify(trackService).instanceTrack(coordinatesDto, trafficOrder);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test adding current coordinates with invalid latitude")
    void addCurrentCoordinatesWithInvalidLatitude() {
        //GIVEN
          CurrentCoordinatesDto coordinatesDto = currentCoordinatesDtoForSavingWithInvalidLatitude();
//
        //WHEN
        mockMvc.perform(post("http://localhost:8080/trip/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coordinatesDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field").value("latitude"))
                .andExpect(jsonPath("$..message").
                        value("For latitude, use values in the range -90 to 90"));

    }

    @Test
    @SneakyThrows
    @DisplayName("Test adding current coordinates with invalid longitude")
    void addCurrentCoordinatesWithInvalidLongitude() {
        //GIVEN
        CurrentCoordinatesDto coordinatesDto = currentCoordinatesDtoForSavingWithInvalidLongitude();

        //WHEN
        mockMvc.perform(post("http://localhost:8080/trip/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coordinatesDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field").value("longitude"))
                .andExpect(jsonPath("$..message").
                        value("For longitude, use values in the range -180 to 180"));

    }
}