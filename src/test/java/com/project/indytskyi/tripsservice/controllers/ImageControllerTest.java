package com.project.indytskyi.tripsservice.controllers;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.impl.ImageS3ServiceImpl;
import com.project.indytskyi.tripsservice.services.impl.TrafficOrderServiceImpl;
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

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrafficOrderServiceImpl trafficOrderService;

    @MockBean
    private ImageS3ServiceImpl imageS3Service;

    @SneakyThrows
    @Test
    void getTrafficOrderImages() {
        TrafficOrderEntity trafficOrderEntity = createTrafficOrder();
        trafficOrderEntity.setImages(List.of(new ImagesEntity(1, trafficOrderEntity, "photo/" + TRAFFIC_ORDER_ID + "/spring.png")));
        //WHEN
        when(trafficOrderService
                .findOne(TRAFFIC_ORDER_ID)).thenReturn(trafficOrderEntity);

        mockMvc.perform(get("http://localhost:8080/trip/" + TRAFFIC_ORDER_ID + "/images")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //THEN
        verify(trafficOrderService).findOne(TRAFFIC_ORDER_ID);

    }

//    @SneakyThrows
//    @Test
//    void downloadImage() {
//        //GIVEN
//        File file = new File("./src/test/resources/umlDiagramOfEntity.png");
//        FileInputStream fileInputStream = new FileInputStream(file);
//
//        //WHEN
//        when(imageS3Service.downloadFile(anyString())).thenReturn(IOUtils.toByteArray(fileInputStream));
//
//        mockMvc.perform(get("http://localhost:8080/trip/image")
//                        .contentType(MediaType.IMAGE_PNG)
//                        .header("path", "src/test/resources/umlDiagramOfEntity.png"))
//                .andExpect(status().isOk());
//
//    }
}