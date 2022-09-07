package com.project.indytskyi.tripsservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageS3Service {

    String saveFile(long trafficOrderId, MultipartFile file);
}
