package com.project.indytskyi.tripsservice;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@Generated
@SpringBootApplication
@EnableEurekaClient
public class TripsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripsServiceApplication.class, args);
    }

}
