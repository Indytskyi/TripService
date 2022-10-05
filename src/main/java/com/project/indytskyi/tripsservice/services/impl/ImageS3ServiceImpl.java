package com.project.indytskyi.tripsservice.services.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.indytskyi.tripsservice.exceptions.DamagedFileException;
import com.project.indytskyi.tripsservice.services.ImageS3Service;
import java.io.IOException;
import java.net.URL;
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

            s3.putObject(bucketName,
                    originalFilename,
                    file.getInputStream(),
                    metadata);

            return originalFilename;
        } catch (IOException e) {
            throw new DamagedFileException("The file is corrupted, try another one");
        }
    }

    @Override
    public URL downloadFile(String path) {
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;

        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, path)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        return s3.generatePresignedUrl(generatePresignedUrlRequest);

    }



}
