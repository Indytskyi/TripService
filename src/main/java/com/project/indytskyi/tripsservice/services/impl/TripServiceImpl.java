package com.project.indytskyi.tripsservice.services.impl;

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
import com.project.indytskyi.tripsservice.services.TripService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl implements TripService {

    private final TrackService trackService;
    private final TrafficOrderService trafficOrderService;
    private final CarService carService;
    private final BackOfficeService backOfficeService;
    private final ImageS3Service imageS3Service;
    private final ImageService imageService;
    private final KafkaService kafkaService;

    private final StartMapper startMapper;

    @Override
    public TripStartDto startTrip(TripActivationDto tripActivation) {
        log.info("Start trip");
        String carClass = carService.getCarInfo(tripActivation);
        carService.setCarStatus(tripActivation.getCarId());

        tripActivation.setTariff(350);
        TrafficOrderEntity trafficOrder = trafficOrderService.save(tripActivation);
        TrackEntity track = trackService.saveStartTrack(trafficOrder, tripActivation);
        return createTripStartDto(trafficOrder, track);
    }

    @Override
    public TripFinishDto finishTrip(long trafficOrderId, List<MultipartFile> files) {
        TrafficOrderEntity trafficOrder = trafficOrderService
                .findOne(trafficOrderId);

        files.forEach(file -> {
            String originalFilename = imageS3Service.saveFile(trafficOrderId, file);
            imageService.saveImages(trafficOrder, originalFilename);
        });

        TripFinishDto tripFinishDto = trafficOrderService.finishOrder(trafficOrder);

        kafkaService.sendOrderToCarService(tripFinishDto);
        kafkaService.sendOrderToBackOfficeService(trafficOrder, tripFinishDto.getTripPayment());

        log.info("finish order and send all information to another services");

        return tripFinishDto;
    }

    /**
     * this method create instance of class TripStartDTO which contains all information
     * about new traffic order and start track
     * This method is used to display, after the start of the trip
     */
    private TripStartDto createTripStartDto(TrafficOrderEntity trafficOrderEntity,
                                            TrackEntity trackEntity) {
        TripStartDto tripStartDto = startMapper
                .toStartDto(trafficOrderEntity, trackEntity);

        tripStartDto.setOwnerId(trafficOrderEntity.getId());
        tripStartDto.setTrackId(trackEntity.getId());
        return tripStartDto;
    }
}
