package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface TripService {

    /**
     * method where you start your work
     * initialization of traffic order and create start track
     *
     */
    TripStartDto startTrip(TripActivationDto tripActivation);

    /**
     * Method where you finish your order and send json to another microservices
     * and save pictures to S3
     */
    TripFinishDto finishTrip(long trafficOrderId, List<MultipartFile> files);
}