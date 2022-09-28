package com.project.indytskyi.tripsservice.services.impl;


import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    private final MockWebServer mockWebServer = new MockWebServer();

    @InjectMocks
    private CarServiceImpl underTest;

    @Value("${car.url}")
    private String basicUrlCar;

    @Spy
    private final WebClient carWebClient = WebClient.create(basicUrlCar);


//    @SneakyThrows
//    @Test
//    void getCarInfo() {
//        mockWebServer.start(8084);
//        mockWebServer.enqueue(
//                new MockResponse()
//                        .setResponseCode(200)
//                        .addHeader("Content-type", "application/json")
//                        .setBody("   \"carClass\": \"MEDIUM\"," +
//                                        " \"coordinates\": {\n" +
//                                "      \"latitude\": 49.87,\n" +
//                                "      \"longitude\": 24.15\n" +
//                                "    }" +
//                                "}")
//        );
//
//        TripActivationDto tripActivationDto = createTripActivationDto();
//        String expected = underTest.getCarInfo(tripActivationDto);
//
//        mockWebServer.shutdown();
//        Assertions.assertNotNull(expected);
//    }


//    @BeforeEach
//    void setUp() throws IOException {
//        server = new MockWebServer();
//        server.start();
//        String rootUrl = server.url("/api/").toString();
//        underTest = new CarServiceImpl(WebClient.create(rootUrl));
//    }
//
//    @AfterEach
//    void tearDown() throws IOException {
//        server.shutdown();
//    }


//    @Test
//    void getCarInfo() {
//        TripActivationDto tripActivationDto = createTripActivationDto();
//        String expected = underTest.getCarInfo(tripActivationDto);
//        webTestClient
//                .get()
//                .uri(basicUrlCar + 1)
//                .header(HttpHeaders.ACCEPT, "application/json")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(CarDto.class);
////        mockWebServer
////                .enqueue(
////                        new MockResponse()
////                                .setResponseCode(200)
////                                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
////                                .setBody(Object)
////                );
//    }

    @Test
    void setCarStatus() {
    }

    @Test
    void setCarAfterFinishingOrder() {
    }
}