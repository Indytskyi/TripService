package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;

public interface ImageService {
    /**
     * Save new image to DB
     * @param ownerOrder
     * @param image
     * @return new Image {@link ImagesEntity}
     */
    ImagesEntity saveImage(TrafficOrderEntity ownerOrder, String image);

}
