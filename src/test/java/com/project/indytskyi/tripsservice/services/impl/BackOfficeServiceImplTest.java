package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.dto.backoffice.CarTariffInformationDtoFactory.createCARTariffInformationDto;
import static com.project.indytskyi.tripsservice.factory.dto.car.CarDtoFactory.createCarDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.indytskyi.tripsservice.dto.backoffice.CarTariffInformationDto;
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
        var expected = createCARTariffInformationDto();
        CarDto carDto = createCarDto();
        String token = "sdlkfjsdklfjsdk";
        //WHEN
        mockWebServer.start(8083);
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-type", "application/json")
                        .setBody("""
                                {
                                  "name":"family",
                                  "description":"good for family trips",
                                  "carType":"medium",
                                  "currency" : "USD",
                                  "ratePerHour":200
                                }
                                """)
        );

        //THEN
        CarTariffInformationDto response = underTest.getCarTariffResponse(carDto, token);

        mockWebServer.shutdown();
        Assertions.assertNotNull(response);
        assertEquals(expected, response);

    }
}