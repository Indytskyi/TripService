package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import com.project.indytskyi.tripsservice.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImagesRepository imagesRepository;

    @Override
    public void saveImages(TrafficOrderEntity ownerOrder,
                           String originFileName) {
        imagesRepository.save(ImagesEntity.of()
                .image(originFileName)
                .ownerImage(ownerOrder)
                .build());
    }

}
