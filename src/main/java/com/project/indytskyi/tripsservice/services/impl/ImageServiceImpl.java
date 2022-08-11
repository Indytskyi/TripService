package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import com.project.indytskyi.tripsservice.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImagesRepository imagesRepository;

    @Override
    public void saveImages(TrafficOrderEntity ownerOrder, List<String> images) {
        images.forEach(image -> {
            ImagesEntity imagesEntity = new ImagesEntity();
            imagesEntity.setImage(image);
            imagesEntity.setOwnerImage(ownerOrder);
            imagesRepository.save(imagesEntity);
        });
    }

}
