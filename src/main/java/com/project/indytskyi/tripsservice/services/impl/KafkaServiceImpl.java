package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.backoffice.BackOfficeDto;
import com.project.indytskyi.tripsservice.dto.car.CarUpdateInfoAfterTripDto;
import com.project.indytskyi.tripsservice.dto.car.StartCoordinatesOfCarDto;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.KafkaService;
import com.project.indytskyi.tripsservice.util.CarStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

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
                .carId((int) trafficOrder.getUserId())
                .userId((int) trafficOrder.getUserId())
                .startDateTime(trafficOrder.getActivationTime())
                .endDateTime(trafficOrder.getCompletionTime())
                .tariff(trafficOrder.getTariff())
                .build();

        backOfficeDtoKafkaTemplate.send(kafkaBackOfficeTopic, backOfficeDto);
    }

    @Override
    public void sendOrderToCarService(TripFinishDto tripFinishDto) {
        log.info("set information to car after the end of the trip");

        CarUpdateInfoAfterTripDto carUpdateInfoAfterTripDto = new CarUpdateInfoAfterTripDto();
        carUpdateInfoAfterTripDto.setCarStatus(String.valueOf(CarStatus.FREE));
        carUpdateInfoAfterTripDto.setCoordinates(new StartCoordinatesOfCarDto(
                tripFinishDto.getLatitude(),
                tripFinishDto.getLongitude()
        ));
        carUpdateInfoAfterTripDto.setDistanceInKilometers(tripFinishDto.getDistance());
        carUpdateInfoAfterTripDto.setFuelLevel(5);

        carFinishDtoKafkaTemplate.send(kafkaCarTopic, carUpdateInfoAfterTripDto);
    }

}