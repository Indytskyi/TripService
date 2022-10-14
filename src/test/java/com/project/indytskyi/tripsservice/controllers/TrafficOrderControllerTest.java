package com.project.indytskyi.tripsservice.controllers;

import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_DISTANCE;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_ID;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_LATITUDE;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_LONGITUDE;
import static com.project.indytskyi.tripsservice.factory.dto.TrackDtoFactory.TRACK_DTO_SPEED;
import static com.project.indytskyi.tripsservice.factory.dto.TrafficOrderDtoFactory.createTrafficOrderDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.createTripActivationDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.createTripFinishDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripStartDtoFactory.createTripStartDto;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.createTrack;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_CAR_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_STATUS;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_TARIFF;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_USER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.indytskyi.tripsservice.dto.StatusDto;
import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.mapper.TrafficOrderDtoMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.TripService;
import com.project.indytskyi.tripsservice.services.UserService;
import com.project.indytskyi.tripsservice.validations.AccessTokenValidation;
import java.util.ArrayList;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@WebMvcTest(TrafficOrderController.class)
@Testcontainers
class TrafficOrderControllerTest {


    @Container
    public static PostgreSQLContainer container =
            (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
                    .withExposedPorts(8080);


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TripService tripService;
    @MockBean
    private TrafficOrderDtoMapper trafficOrderDtoMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AccessTokenValidation accessTokenValidation;

    @Test
    @SneakyThrows
    @DisplayName("Test finding a track by id")
    void getTrackById() {
        //GIVEN
        TrafficOrderDto trafficOrderDto = createTrafficOrderDto();
        String token = "test";
        //WHEN
        when(tripService.getTripById(TRAFFIC_ORDER_ID)).thenReturn(trafficOrderDto);

        mockMvc.perform(get("http://localhost:8080/trip/" + TRAFFIC_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TRAFFIC_ORDER_ID))
                .andExpect(jsonPath("$.carId").value(TRAFFIC_ORDER_CAR_ID))
                .andExpect(jsonPath("$.userId").value(TRAFFIC_ORDER_USER_ID))
                .andExpect(jsonPath("$.status").value(TRAFFIC_ORDER_STATUS))
                .andExpect(jsonPath("$.tariff").value(TRAFFIC_ORDER_TARIFF));

        //THEN
        verify(tripService).getTripById(TRAFFIC_ORDER_ID);

    }


    @Test
    @SneakyThrows
    @DisplayName("Test finding a track by non existent id")
    void getTrackByNonExistentId() {
        //GIVEN
        when(tripService.getTripById(TRAFFIC_ORDER_ID))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        String token = "test";

        //WHEN
        mockMvc.perform(get("http://localhost:8080/trip/" + TRAFFIC_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andExpect(status().isNotFound());

        //THEN
        verify(tripService).getTripById(TRAFFIC_ORDER_ID);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test saving traffic order and first coordinates of car")
    void saveTrafficOrderAndStartTrack() {
        //GIVEN
        TripActivationDto tripActivationDto = createTripActivationDto();
        TrackEntity track = createTrack();
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TripStartDto tripStartDto = createTripStartDto();
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(22L);
        String token = "test";
        //WHEN
        when(tripService.startTrip(tripActivationDto, token)).thenReturn(tripStartDto);
        when(userService.validateToken(token)).thenReturn(responseDto);
        mockMvc.perform(post("http://localhost:8080/trip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripActivationDto))
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tripId").value(TRAFFIC_ORDER_ID))
                .andExpect(jsonPath("$.carId").value(TRAFFIC_ORDER_CAR_ID))
                .andExpect(jsonPath("$.userId").value(TRAFFIC_ORDER_USER_ID))
                .andExpect(jsonPath("$.status").value(TRAFFIC_ORDER_STATUS))
                .andExpect(jsonPath("$.trackId").value(TRACK_DTO_ID))
                .andExpect(jsonPath("$.latitude").value(TRACK_DTO_LATITUDE))
                .andExpect(jsonPath("$.longitude").value(TRACK_DTO_LONGITUDE))
                .andExpect(jsonPath("$.speed").value(TRACK_DTO_SPEED))
                .andExpect(jsonPath("$.distance").value(TRACK_DTO_DISTANCE))
                .andExpect(jsonPath("$.tariff").value(TRAFFIC_ORDER_TARIFF));

        //THEN
    }

    @Test
    @SneakyThrows
    @DisplayName("Test saving traffic order and first coordinates of car")
    void saveTrafficOrderAndStartTrack1() {
        //GIVEN
        TripActivationDto tripActivationDto = createTripActivationDto();
        TripStartDto tripStartDto = createTripStartDto();
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(21L);
        String token = "test";
        //WHEN
        when(tripService.startTrip(tripActivationDto, token)).thenReturn(tripStartDto);
        when(userService.validateToken(token)).thenReturn(responseDto);
        mockMvc.perform(post("http://localhost:8080/trip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripActivationDto))
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    @DisplayName("Test stopping traffic order ")
    void stopTrafficOrder() {
        //GIVEN
        String token = "test";
        StatusDto statusDto = new StatusDto();
        statusDto.setStatus("STOP");
        //WHEN
        mockMvc.perform(patch("http://localhost:8080/trip/" + TRAFFIC_ORDER_ID + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto))
                        .header("Authorization", token))
                .andExpect(status().isOk());

        //THEN
        verify(tripService).changeTripStatus(TRAFFIC_ORDER_ID, statusDto);

    }

//    @Test
//    @SneakyThrows
//    @DisplayName("Test finishing traffic order with incorrect type of multipartsfiles")
//    void finishTrafficOrderWithIncorrectTypeOfMultipartFiles() {
//        //GIVEN
//        MockMultipartFile file
//                = new MockMultipartFile(
//                "files",
//                "hello.txt",
//                MediaType.TEXT_PLAIN_VALUE,
//                "Hello, World!".getBytes()
//        );
//        List<MultipartFile> files = List.of(file);
////        List<ErrorResponse> errorResponses = List.of(new ErrorResponse("hello.txt",
////                "Incorrect format of file. Only images with format (jpg or png)"));
//        //WHEN
//        mockMvc.perform(multipart("http://localhost:8080/trip/" + 1L)
//                .file(file))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$..field").value("hello.txt"));
//
//        //THEN
//    }

    @Test
    @SneakyThrows
    @DisplayName("Test finishing traffic order and save images")
    void finishTrafficOrder() {
        //GIVEN
        TripFinishDto tripFinishDto = createTripFinishDto();
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        String token = "test";

        MockMultipartFile multipartFile
                = new MockMultipartFile(
                "files",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        List<MultipartFile> files = List.of(multipartFile);
        List<ErrorResponse> errorResponses = new ArrayList<>();

        //WHEN
        when(tripService.finishTrip(TRAFFIC_ORDER_ID, files)).thenReturn(tripFinishDto);
        mockMvc.perform(multipart("http://localhost:8080/trip/" + 1 + "/photos")
                        .file(multipartFile)
                        .header("Authorization", token))
                .andExpect(status().isOk());

        //THEN
    }



}