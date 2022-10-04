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

    private final KafkaTemplate<String, BackOfficeDto> backOfficeDtoKafkaTemplate;
    private final KafkaTemplate<String, CarUpdateInfoAfterTripDto> carFinishDtoKafkaTemplate;
    private final double defaultFuelLevelLiter = 5;

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
                .build();
        System.out.println(backOfficeDto);
        backOfficeDtoKafkaTemplate.send(kafkaBackOfficeTopic, backOfficeDto);
    }

    @Override
    public void sendOrderToCarService(TripFinishDto tripFinishDto) {
        log.info("set information to car with id = {} after the end of the trip",
                tripFinishDto.getCarId());

        CarUpdateInfoAfterTripDto carUpdateInfoAfterTripDto = new CarUpdateInfoAfterTripDto();
        carUpdateInfoAfterTripDto.setCarStatus(String.valueOf(CarStatus.FREE));
        carUpdateInfoAfterTripDto.setCoordinates(new StartCoordinatesOfCarDto(
                tripFinishDto.getLatitude(),
                tripFinishDto.getLongitude()
        ));
        carUpdateInfoAfterTripDto.setDistanceInKilometers(tripFinishDto.getDistance());
        carUpdateInfoAfterTripDto.setFuelLevelLiter(defaultFuelLevelLiter);
        carUpdateInfoAfterTripDto.setId(tripFinishDto.getCarId());
        System.out.println(carUpdateInfoAfterTripDto);
        carFinishDtoKafkaTemplate.send(kafkaCarTopic, carUpdateInfoAfterTripDto);
    }

}
