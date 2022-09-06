package com.project.indytskyi.tripsservice.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.ImagesRepository;
import com.project.indytskyi.tripsservice.services.ImageService;
import java.io.IOException;
import java.util.List;
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
    public void saveImages(TrafficOrderEntity ownerOrder, List<MultipartFile> images) {
        images.forEach(image -> {
            ImagesEntity imagesEntity = new ImagesEntity();
            imagesEntity.setImage(image.getOriginalFilename());
            imagesEntity.setOwnerImage(ownerOrder);
            imagesRepository.save(imagesEntity);
        });
    }

    @Override
    public String saveFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            PutObjectResult putObjectResult = s3.putObject(bucketName,
                    originalFilename,
                    file.getInputStream(),
                    metadata);

            return putObjectResult.getContentMd5();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
