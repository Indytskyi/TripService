package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.LInksToImagesDto;
import com.project.indytskyi.tripsservice.dto.StatusDto;
import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
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
    TripStartDto startTrip(TripActivationDto tripActivation, String token);

    /**
     * Method where you finish your order and send json to another microservices
     * and save pictures to S3
     */
    TripFinishDto finishTrip(long trafficOrderId, List<MultipartFile> files, String token);

    /**
     * Get information about trip by id
     * + if, user has finished his trip,
     * the admin also get links to photos
     */
    TrafficOrderDto getTripById(long trafficOrderId, String token);


    /**
     * change status of trip
     *
     */
    TrafficOrderDto changeTripStatus(long trafficOrderId, StatusDto statusDto, String token);

    LInksToImagesDto generatingDownloadLinks(long trafficOrderId, String token);
}
