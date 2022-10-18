package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.LInksToImagesDto;
import com.project.indytskyi.tripsservice.dto.StatusDto;
import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.dto.backoffice.CarTariffInformationDto;
import com.project.indytskyi.tripsservice.dto.car.CarDto;
import com.project.indytskyi.tripsservice.exceptions.enums.StatusException;
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
import com.project.indytskyi.tripsservice.validations.ServiceValidation;
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
    private final ServiceValidation serviceValidation;

    @Override
    public TripStartDto startTrip(TripActivationDto tripActivation, String token) {

        serviceValidation
                .validateActiveCountOfTrafficOrders(tripActivation.getUserId());

        log.info("Start trip with carId = {} and userId = {}",
                tripActivation.getCarId(),
                tripActivation.getUserId());

        CarDto carDto = carService.getCarInfo(tripActivation, token);

        final CarTariffInformationDto carTariffInformationDto =
                backOfficeService.getCarTariffResponse(carDto, token);

        carService.setCarStatus(tripActivation.getCarId(), token);

        tripActivation.setLongitude(carDto.getCoordinates().getLongitude());
        tripActivation.setLatitude(carDto.getCoordinates().getLatitude());
        tripActivation.setTariff(carTariffInformationDto.getRatePerHour());
        tripActivation.setCurrency(carTariffInformationDto.getCurrency());
        tripActivation.setTariffId(carTariffInformationDto.getId());
        tripActivation.setUnitOfSpeed(carTariffInformationDto.getUnitOfSpeed());

        TrafficOrderEntity trafficOrder = trafficOrderService.save(tripActivation);
        TrackEntity track = trackService.saveStartTrack(trafficOrder, tripActivation);

        return createTripStartDto(trafficOrder, track);
    }

    @Override
    public TripFinishDto finishTrip(long trafficOrderId, List<MultipartFile> files) {

        TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(trafficOrderId);

        serviceValidation.validateStatusAccess(trafficOrder.getStatus(),
                Status.STOP.name(),
                StatusException.FORGET_STOP_EXCEPTION.getException());

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
    public TrafficOrderDto changeTripStatus(long trafficOrderId, StatusDto statusDto) {

        TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(trafficOrderId);

        serviceValidation.validationForStatusChange(statusDto.getStatus(),
                trafficOrder.getStatus());

        trafficOrder.setStatus(statusDto.getStatus());
        return trafficOrderDtoMapper.toTrafficOrderDto(trafficOrder);
    }

    @Override
    public LInksToImagesDto generatingDownloadLinks(long trafficOrderId) {

        TrafficOrderEntity trafficOrder = trafficOrderService
                .findTrafficOrderById(trafficOrderId);

        serviceValidation.validateStatusAccess(trafficOrder.getStatus(),
                Status.FINISH.name(),
                StatusException.UNFINISHED_TRIP_EXCEPTION.getException());

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
