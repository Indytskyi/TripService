package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import java.util.List;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final MockWebServer mockWebServer = new MockWebServer();

    @InjectMocks
    private UserServiceImpl underTest;


    private String basicUrlUser = "http://localhost:8082";

    @Spy
    private final WebClient carWebClient = WebClient.create(basicUrlUser);

    @SneakyThrows
    @Test
    void validateToken() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(TRAFFIC_ORDER_USER_ID);
        responseDto.setRoles(List.of("ADMIN"));
        String token = "test";

        //WHEN
        mockWebServer.start(8082);
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-type", "application/json")
                        .setBody("""
                                {
                                    "userId" : 22,
                                    "roles" : [
                                        "ADMIN"
                                    ]
                                }
                                """)
        );

        var response =  underTest.validateToken(token);

        mockWebServer.shutdown();
        Assertions.assertNotNull(response);
        assertEquals(responseDto, response);
    }

}