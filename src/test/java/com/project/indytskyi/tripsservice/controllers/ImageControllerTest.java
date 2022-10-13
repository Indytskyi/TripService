package com.project.indytskyi.tripsservice.controllers;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.services.TripService;
import com.project.indytskyi.tripsservice.services.UserService;
import com.project.indytskyi.tripsservice.validations.AccessTokenValidation;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@WebMvcTest(ImageController.class)
@Testcontainers
class ImageControllerTest {

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
    @MockBean
    private AccessTokenValidation accessTokenValidation;
    @MockBean
    private TripService tripService;
    @MockBean
    private UserService userService;

    @SneakyThrows
    @Test
    void downloadImage() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(TRAFFIC_ORDER_USER_ID);
        responseDto.setRoles(List.of("ADMIN"));
        String token = "Hello";

        //WHEN
        when(userService.validateToken(anyString())).thenReturn(responseDto);
        doNothing().when(accessTokenValidation).checkIfTheConsumerIsAdmin(responseDto);
        when(tripService.generatingDownloadLinks(TRAFFIC_ORDER_ID)).thenReturn(any());
        mockMvc.perform(get("http://localhost:8080/trip/" + TRAFFIC_ORDER_ID + "/image")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}