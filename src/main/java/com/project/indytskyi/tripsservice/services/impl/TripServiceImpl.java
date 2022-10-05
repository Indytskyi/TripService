package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.LInksToImagesDto;
import com.project.indytskyi.tripsservice.dto.StatusDto;
import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
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
import com.project.indytskyi.tripsservice.services.TripService;
import com.project.indytskyi.tripsservice.util.enums.Status;
import java.net.URL;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final TrafficOrderDtoMapper trafficOrderDtoMapper;

    @Override
    public TripStartDto startTrip(TripActivationDto tripActivation) {
        log.info("Start trip");
        String carClass = carService.getCarInfo(tripActivation);
        carService.setCarStatus(tripActivation.getCarId());
        tripActivation.setTariff(backOfficeService.getCarTariff(carClass));
        TrafficOrderEntity trafficOrder = trafficOrderService.save(tripActivation);
        TrackEntity track = trackService.saveStartTrack(trafficOrder, tripActivation);
        return createTripStartDto(trafficOrder, track);
    }

    @Override
    public TripFinishDto finishTrip(long trafficOrderId, List<MultipartFile> files) {
        TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(trafficOrderId);

        if (!trafficOrder.getStatus().equals(Status.STOP.name())) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    "Pause the car to end the trip")));
        }

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

    @Override
    public TrafficOrderDto getTripById(long trafficOrderId) {
        TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(trafficOrderId);

        TrafficOrderDto trafficOrderDto = trafficOrderDtoMapper.toTrafficOrderDto(trafficOrder);
        trafficOrderDto.setPhotos(getAllPathFromTrip(trafficOrder));

        return trafficOrderDto;
    }

    @Transactional
    @Override
    public TrafficOrderDto changeTripStatus(long trafficOrderId,
                                            StatusDto statusDto) {
        TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(trafficOrderId);

        if (!statusDto.getStatus().equals(Status.IN_ORDER.name())
                && !statusDto.getStatus().equals(Status.STOP.name())) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    "Incorrect data entry: '"
                            + statusDto.getStatus()
                            + " in status; "
                            + "Possible values: "
                            + "[IN_ORDER, STOP]")));
        }

        if (trafficOrder.getStatus().equals(Status.FINISH.name())) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    "This trip is over, start another one")));
        }

        trafficOrder.setStatus(statusDto.getStatus());
        return trafficOrderDtoMapper.toTrafficOrderDto(trafficOrder);
    }

    @Override
    public LInksToImagesDto generatingDownloadLinks(long trafficOrderId) {
        TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(trafficOrderId);

        if (!trafficOrder.getStatus().equals(Status.FINISH.name())) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    "The user has not yet completed a trip")));
        }

        List<URL> imageS3Urls = getAllPathFromTrip(trafficOrder)
                .stream()
                .map(imageS3Service::downloadFile)
                .toList();
        return LInksToImagesDto.of()
                .tripId(trafficOrderId)
                .imageUrls(imageS3Urls)
                .build();
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

        tripStartDto.setTripId(trafficOrderEntity.getId());
        tripStartDto.setTrackId(trackEntity.getId());
        return tripStartDto;
    }

    private List<String> getAllPathFromTrip(TrafficOrderEntity trafficOrder) {
        return trafficOrder.getImages()
                .stream()
                .map(ImagesEntity::getImage)
                .toList();
    }
}
