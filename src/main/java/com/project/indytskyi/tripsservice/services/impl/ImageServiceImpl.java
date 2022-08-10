package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import com.project.indytskyi.tripsservice.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImagesRepository imagesRepository;

    @Override
    public ImagesEntity saveImage(TrafficOrderEntity ownerOrder, String image) {
        ImagesEntity imagesEntity = new ImagesEntity();
        imagesEntity.setImage(image);
        imagesEntity.setOwnerImage(ownerOrder);
        return imagesRepository.save(imagesEntity);
    }

}
