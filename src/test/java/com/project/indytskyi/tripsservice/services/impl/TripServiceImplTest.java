package com.project.indytskyi.tripsservice.services.impl;

import static com.project.indytskyi.tripsservice.factory.dto.TripActivationDtoFactory.createTripActivationDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripFinishDtoFactory.createTripFinishDto;
import static com.project.indytskyi.tripsservice.factory.dto.TripStartDtoFactory.createTripStartDto;
import static com.project.indytskyi.tripsservice.factory.model.TrackFactory.createTrack;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.util.IOUtils;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.mapper.StartMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.BackOfficeService;
import com.project.indytskyi.tripsservice.services.CarService;
import com.project.indytskyi.tripsservice.services.ImageS3Service;
import com.project.indytskyi.tripsservice.services.ImageService;
import com.project.indytskyi.tripsservice.services.KafkaService;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import java.io.File;
import java.io.FileInputStream;
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

    @InjectMocks
    private TripServiceImpl underTest;

    @Test
    void startTrip() {
        //GIVEN
        TripActivationDto tripActivationDto = createTripActivationDto();
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        TrackEntity track = createTrack();
        TripStartDto tripStartDto = createTripStartDto();
        String carClass = "ordinary";

        //WHEN
        when(carService.getCarInfo(tripActivationDto)).thenReturn(carClass);
        doNothing().when(carService).setCarStatus(anyLong());
        when(trafficOrderService.save(tripActivationDto)).thenReturn(trafficOrder);
        when(trackService.saveStartTrack(trafficOrder, tripActivationDto))
                .thenReturn(track);
        when(startMapper.toStartDto(trafficOrder, track)).thenReturn(tripStartDto);

        //THEN
        underTest.startTrip(tripActivationDto);

        verify(startMapper, times(1))
                .toStartDto(trafficOrder, track);
        verify(trackService, times(1))
                .saveStartTrack(trafficOrder, tripActivationDto);

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
        TrackEntity track = createTrack();
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
}