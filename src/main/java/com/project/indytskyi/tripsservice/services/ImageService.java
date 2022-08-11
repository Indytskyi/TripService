package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;

import java.util.List;

public interface ImageService {
    /**
     * Save new images after car photoshoots to DB
     * @param ownerOrder  = {@link TrafficOrderEntity}
     * @param images = {@link String}
     * @return new Image {@link ImagesEntity}
     */
    void saveImages(TrafficOrderEntity ownerOrder, List<String> images);

}
