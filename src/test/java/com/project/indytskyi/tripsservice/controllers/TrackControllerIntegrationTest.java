package com.project.indytskyi.tripsservice.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.exceptions.TrackNotFoundException;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.services.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class TrackControllerIntegrationTest {

    private MockMvc mvc;

    @InjectMocks
    private TrackController trackController;

    @Mock
    private TrackService trackService;

    private JacksonTester<CurrentCoordinatesDto> jsonCurrentCoordinatesDTO;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(trackController)
                .build();
    }

    @Test
    void getTrackOrder() throws Exception {
        // given
        TrackEntity track = new TrackEntity();
        track.setLongitude(10);
        track.setLatitude(10);
        given(trackService.findOne(2))
                .willReturn(track);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/trip/track/2")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo(
//
//                jsonCurrentCoordinatesDTO.write(new CurrentCoordinatesDTO(10, 10)).getJson()
//        );

    }

    @Test
    void getTrackOrder2() throws Exception {
        // given
        given(trackService.findOne(2))
                .willThrow(new TrackNotFoundException());

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/trip/track/2")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
//        assertThat(response.getContentAsString()).isEqualTo(
//
//                jsonCurrentCoordinatesDTO.write(new CurrentCoordinatesDTO(10, 10)).getJson()
//        );

    }

    @Test
    void saveCurrentCoordinates() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                post("/trip/track/current").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCurrentCoordinatesDTO.
                                write(new CurrentCoordinatesDto(10, 10))
                                .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void ifCurrentCoordinatesThatWeWantToSaveAreIncorrect() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        post("/trip/track/current").contentType(MediaType.APPLICATION_JSON)
                                .content(jsonCurrentCoordinatesDTO.
                                        write(new CurrentCoordinatesDto(-100,10))
                                        .getJson()))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}