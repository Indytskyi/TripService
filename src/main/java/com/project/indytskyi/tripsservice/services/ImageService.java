package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;

public interface ImageService {
    /**
     * Save new images after car photoshoots to DB
     */
    void saveImages(TrafficOrderEntity ownerOrder,
                    String originFileName);

}
