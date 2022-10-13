package com.project.indytskyi.tripsservice.services.impl;


import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.createTripActivationDto;
import static com.project.indytskyi.tripsservice.factory.dto.car.CarDtoFactory.createCarDto;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_CAR_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
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

//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    private final MockWebServer mockWebServer = new MockWebServer();

    @InjectMocks
    private CarServiceImpl underTest;


    private String basicUrlCar = "http://localhost:8084/cars";

    @Spy
    private final WebClient carWebClient = WebClient.create(basicUrlCar);


    @SneakyThrows
    @Test
    void getCarInfo() {
        //GIVEN
        CarDto expected = createCarDto();
        TripActivationDto tripActivationDto = createTripActivationDto();
        //WHEN
        mockWebServer.start(8084);
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-type", "application/json")
                        .setBody("""
                                {
                                "carClass": "MEDIUM",
                                "coordinates": {
                                      "latitude": 49.87,
                                      "longitude": 24.15
                                    }
                                }""")
        );

        //THEN
        CarDto response = underTest.getCarInfo(tripActivationDto);

        mockWebServer.shutdown();
        Assertions.assertNotNull(expected);
        assertEquals(expected, response);

    }

    @SneakyThrows
    @Test
    void setCarStatus() {
        //WHEN
        mockWebServer.start(8084);
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader("carStatus", "RENTED")
                        .addHeader("Content-type", "application/json")
                        .setBody("""
                                {
                                "carStatus": "RENTED"
                                }
                                """)
        );

        //THEN
        underTest.setCarStatus(TRAFFIC_ORDER_CAR_ID);

        mockWebServer.shutdown();
        verify(carWebClient).patch();

    }


}