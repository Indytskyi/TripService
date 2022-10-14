package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.backoffice.BackOfficeDto;
import com.project.indytskyi.tripsservice.dto.car.CarUpdateInfoAfterTripDto;
import com.project.indytskyi.tripsservice.dto.car.StartCoordinatesOfCarDto;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.KafkaService;
import com.project.indytskyi.tripsservice.util.enums.CarStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private static final double DEFAULT_FUEL_LEVEL_LITERS = 5;
    private final KafkaTemplate<String, BackOfficeDto> backOfficeDtoKafkaTemplate;
    private final KafkaTemplate<String, CarUpdateInfoAfterTripDto> carFinishDtoKafkaTemplate;

    @Value("${kafka.backoffice}")
    private String kafkaBackOfficeTopic;
    @Value("${kafka.car}")
    private String kafkaCarTopic;

    @Override
    public void sendOrderToBackOfficeService(TrafficOrderEntity trafficOrder, double price) {
        log.info("Sending BackOfficeDto Json Serializer");
        BackOfficeDto backOfficeDto = BackOfficeDto.of()
                .price(price)
                .carId((int) trafficOrder.getCarId())
                .userId((int) trafficOrder.getUserId())
                .startDateTime(trafficOrder.getActivationTime())
                .endDateTime(trafficOrder.getCompletionTime())
                .ratePerHour(trafficOrder.getTariff())
                .currency(trafficOrder.getCurrency())
                .build();

        backOfficeDtoKafkaTemplate.send(kafkaBackOfficeTopic, backOfficeDto);
    }

    @Override
    public void sendOrderToCarService(TripFinishDto tripFinishDto) {
        log.info("set information to car with id = {} after the end of the trip",
                tripFinishDto.getCarId());

        CarUpdateInfoAfterTripDto carUpdateInfoAfterTripDto =
                CarUpdateInfoAfterTripDto.of()
                        .carStatus(CarStatus.FREE.name())
                        .coordinates(new StartCoordinatesOfCarDto(
                                tripFinishDto.getLatitude(),
                                tripFinishDto.getLongitude()
                        ))
                        .distanceInKilometers(tripFinishDto.getDistance())
                        .fuelLevelLiter(DEFAULT_FUEL_LEVEL_LITERS)
                        .id(tripFinishDto.getCarId())
                        .build();

        carFinishDtoKafkaTemplate.send(kafkaCarTopic, carUpdateInfoAfterTripDto);
    }

}
