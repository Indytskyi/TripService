package com.project.indytskyi.tripsservice.config.kafka;

import com.project.indytskyi.tripsservice.dto.backoffice.BackOfficeDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaBackOfficeProducerConfig extends KafkaConfig {
    @Bean
    public ProducerFactory<String, BackOfficeDto> backOfficeDtoProducerFactory() {

        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, BackOfficeDto> backOfficeDtoKafkaTemplate() {
        return new KafkaTemplate<>(backOfficeDtoProducerFactory());
    }

}
