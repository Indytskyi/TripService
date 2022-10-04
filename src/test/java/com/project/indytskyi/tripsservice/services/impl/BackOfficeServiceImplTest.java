package com.project.indytskyi.tripsservice.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class BackOfficeServiceImplTest {

    private final MockWebServer mockWebServer = new MockWebServer();

    @InjectMocks
    private BackOfficeServiceImpl underTest;


    private String basicUrlCar = "http://localhost:8083/user/";

    @Spy
    private final WebClient backofficeWebClient = WebClient.create(basicUrlCar);

    @SneakyThrows
    @Test
    void getCarTariff() {
        //GIVEN
        Double expected = 200.0;
        String carClass = "MEDIUM";

        //WHEN
        mockWebServer.start(8084);
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-type", "application/json")
                        .setBody("""
                                {
                                  "name":"family",
                                  "description":"good for family trips",
                                  "carType":"medium",
                                  "ratePerHour":200
                                }
                                """)
        );

        //THEN
        Double response = underTest.getCarTariff(carClass);

        mockWebServer.shutdown();
        Assertions.assertNotNull(expected);
        assertEquals(expected, response);

    }
}