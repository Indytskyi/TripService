package com.project.indytskyi.tripsservice.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import com.project.indytskyi.tripsservice.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImagesRepository imagesRepository;
    private final AmazonS3 s3;

    @Value("${bucketName}")
    private String bucketName;

    @Override
    public void saveImages(TrafficOrderEntity ownerOrder,
                           MultipartFile image,
                           String originFileName) {
            ImagesEntity imagesEntity = new ImagesEntity();
            imagesEntity.setImage(originFileName);
            imagesEntity.setOwnerImage(ownerOrder);
            imagesRepository.save(imagesEntity);
    }

}
