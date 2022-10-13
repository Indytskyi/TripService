package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.dto.TrafficOrderDtoFactory.createTrafficOrderDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.createTripActivationDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.createTripFinishDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripStartDtoFactory.createTripStartDto;
import static com.project.indytskyi.tripsservice.factory.dto.car.CarDtoFactory.createCarDto;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.createTrack;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_USER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.amazonaws.util.IOUtils;
import com.project.indytskyi.tripsservice.dto.StatusDto;
import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.mapper.StartMapper;
import com.project.indytskyi.tripsservice.mapper.TrafficOrderDtoMapper;
import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.BackOfficeService;
import com.project.indytskyi.tripsservice.services.CarService;
import com.project.indytskyi.tripsservice.services.ImageS3Service;
import com.project.indytskyi.tripsservice.services.ImageService;
import com.project.indytskyi.tripsservice.services.KafkaService;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import com.project.indytskyi.tripsservice.validations.ServiceValidation;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    @Mock
    private TrackService trackService;
    @Mock
    private TrafficOrderService trafficOrderService;
    @Mock
    private CarService carService;
    @Mock
    private BackOfficeService backOfficeService;
    @Mock
    private ImageS3Service imageS3Service;
    @Mock
    private ImageService imageService;
    @Mock
    private KafkaService kafkaService;
    @Mock
    private StartMapper startMapper;
    @Mock
    private TrafficOrderDtoMapper trafficOrderDtoMapper;
    @Mock
    private ServiceValidation serviceValidation;

    @InjectMocks
    private TripServiceImpl underTest;

    @Test
    void startTrip() {
        //GIVEN
        TripActivationDto tripActivationDto = createTripActivationDto();
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TrackEntity track = createTrack();
        TripStartDto tripStartDto = createTripStartDto();
        CarDto carDto = createCarDto();
        String token = "test";
        double tariff = 200;

        //WHEN
        doNothing().when(serviceValidation).validateActiveCountOfTrafficOrders(TRAFFIC_ORDER_USER_ID);
        when(carService.getCarInfo(tripActivationDto)).thenReturn(carDto);
        doNothing().when(carService).setCarStatus(anyLong());
        when(trafficOrderService.save(tripActivationDto)).thenReturn(trafficOrder);
        when(backOfficeService.getCarTariff(carDto, token)).thenReturn(tariff);
        when(trackService.saveStartTrack(trafficOrder, tripActivationDto))
                .thenReturn(track);
        when(startMapper.toStartDto(trafficOrder, track)).thenReturn(tripStartDto);

        //THEN
        var response = underTest.startTrip(tripActivationDto, token);

        assertEquals(response, tripStartDto);
    }

    @SneakyThrows
    @Test
    void finishTrip() {
        //GIVEN
        File file = new File("./src/test/resources/umlDiagramOfEntity.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                "umlDiagramOfEntity.png",
                "image/png",
                IOUtils.toByteArray(fileInputStream));
        List<MultipartFile> files = List.of(multipartFile);
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TripFinishDto tripFinishDto = createTripFinishDto();
        String originalFileName = "umlDiagramOfEntity.png";

        //WHEN
        when(trafficOrderService.findTrafficOrderById(anyLong())).thenReturn(trafficOrder);
        when(imageS3Service.saveFile(anyLong(), any())).thenReturn(originalFileName);
        doNothing().when(imageService).saveImages(trafficOrder, originalFileName);
        when(trafficOrderService.finishOrder(trafficOrder)).thenReturn(tripFinishDto);
        doNothing().when(kafkaService).sendOrderToCarService(tripFinishDto);
        doNothing().when(kafkaService)
                .sendOrderToBackOfficeService(trafficOrder, tripFinishDto.getTripPayment());

        //THEN
        underTest.finishTrip(trafficOrder.getId(), files);

    }

    @Test
    void canGetTrafficOrderDtoByTripId() {
        //GIVEN
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TrafficOrderDto orderDto = createTrafficOrderDto();
        trafficOrder.setImages(Collections.singletonList(
                ImagesEntity.of()
                        .image("./src/test/resources/umlDiagramOfEntity.png")
                        .ownerImage(trafficOrder)
                        .id(12)
                        .build()
        ));

        //WHEN
        when(trafficOrderService.findTrafficOrderById(TRAFFIC_ORDER_ID)).thenReturn(trafficOrder);
        when(trafficOrderDtoMapper.toTrafficOrderDto(trafficOrder)).thenReturn(orderDto);

        var response = underTest.getTripById(TRAFFIC_ORDER_ID);

        //THEN
        assertEquals(response, response);
    }

    @Test
    void canChangeTripStatus() {
        //GIVEN
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TrafficOrderDto orderDto = createTrafficOrderDto();
        StatusDto statusDto = new StatusDto();
        statusDto.setStatus("STOP");

        //WHEN
        when(trafficOrderService.findTrafficOrderById(TRAFFIC_ORDER_ID)).thenReturn(trafficOrder);
        doNothing().when(serviceValidation).validationForStatusChange(anyString(), anyString());
        when(trafficOrderDtoMapper.toTrafficOrderDto(trafficOrder)).thenReturn(orderDto);

        var response = underTest.changeTripStatus(TRAFFIC_ORDER_ID, statusDto);

        //THEN
        assertEquals(orderDto, response);
    }

    @Test
    void canGenerateDownloadingLinks() {
        //GIVEN
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        trafficOrder.setImages(Collections.singletonList(
                ImagesEntity.of()
                        .image("./src/test/resources/umlDiagramOfEntity.png")
                        .ownerImage(trafficOrder)
                        .id(12)
                        .build()
        ));
        //WHEN
        when(trafficOrderService.findTrafficOrderById(TRAFFIC_ORDER_ID)).thenReturn(trafficOrder);
        doNothing().when(serviceValidation).validateStatusAccess(anyString(),anyString(), any());

        var response = underTest.generatingDownloadLinks(TRAFFIC_ORDER_ID);
    }

}