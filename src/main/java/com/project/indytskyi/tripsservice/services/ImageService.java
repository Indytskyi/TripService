package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;

public interface ImageService {
    /**
     * Save new image to DB
     * @param ownerOrder  = {@link TrafficOrderEntity}
     * @param image = {@link String}
     * @return new Image {@link ImagesEntity}
     */
    ImagesEntity saveImage(TrafficOrderEntity ownerOrder, String image);

}
