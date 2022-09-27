package com.project.indytskyi.tripsservice.services.impl;


import com.project.indytskyi.tripsservice.services.CarService;
import java.io.IOException;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.reactive.function.client.WebClient;

class CarServiceImplTest {

    @InjectMocks
    private CarService underTest;

    private MockWebServer server;


    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        String rootUrl = server.url("/api/").toString();
        underTest = new CarServiceImpl(WebClient.create(rootUrl));
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }


    @Test
    void getCarInfo() {
    }

    @Test
    void setCarStatus() {
    }

    @Test
    void setCarAfterFinishingOrder() {
    }
}