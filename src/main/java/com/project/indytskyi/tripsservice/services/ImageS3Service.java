package com.project.indytskyi.tripsservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageS3Service {

    /**
     * save files in s3 basket
     * with path
     * if we start service in docker
     * (docker/photos/trafficOrderId/...)
     * if we start service without docker
     * (photos/trafficOrderId/...)
     */
    String saveFile(long trafficOrderId, MultipartFile file);

    /**
     * Download files from s3 server
     * by path of this photo.
     * and return photo in byte format
     */
    byte[] downloadFile(String path);
}
