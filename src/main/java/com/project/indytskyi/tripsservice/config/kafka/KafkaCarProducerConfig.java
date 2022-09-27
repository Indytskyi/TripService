package com.project.indytskyi.tripsservice.config.kafka;

import com.project.indytskyi.tripsservice.dto.car.CarUpdateInfoAfterTripDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaCarProducerConfig extends KafkaConfig {

    @Bean
    public ProducerFactory<String, CarUpdateInfoAfterTripDto> carDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, CarUpdateInfoAfterTripDto> carDtoKafkaTemplate() {
        return new KafkaTemplate<>(carDtoProducerFactory());
    }

}
