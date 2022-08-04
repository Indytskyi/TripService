package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImagesRepository imagesRepository;

    public ImagesEntity saveImage(TrafficOrderEntity ownerOrder, String image) {
        ImagesEntity imagesEntity = new ImagesEntity();
        imagesEntity.setImage(image);
        imagesEntity.setOwnerImage(ownerOrder);
        return imagesRepository.save(imagesEntity);
    }

}
