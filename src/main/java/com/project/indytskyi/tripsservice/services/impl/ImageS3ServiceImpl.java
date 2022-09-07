package com.project.indytskyi.tripsservice.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.project.indytskyi.tripsservice.services.ImageS3Service;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageS3ServiceImpl implements ImageS3Service {

    private final AmazonS3 s3;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${s3Folder}")
    private String s3Folder;

    @Override
    public String saveFile(long trafficOrderId, MultipartFile file) {
        String originalFilename = String.format("%s/%s/%s", s3Folder,
                trafficOrderId,
                file.getOriginalFilename());
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            PutObjectResult putObjectResult = s3.putObject(bucketName,
                    originalFilename,
                    file.getInputStream(),
                    metadata);

            return originalFilename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] downloadFile(String path) {
        S3Object object = s3.getObject(bucketName, path);

        S3ObjectInputStream objectContent = object.getObjectContent();
        try {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
