package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    /**
     * Save new images after car photoshoots to DB
     * @param ownerOrder  = {@link TrafficOrderEntity}
     * @param images = {@link String}
     * @return new Image {@link ImagesEntity}
     */
    void saveImages(TrafficOrderEntity ownerOrder, List<MultipartFile> images);

    String saveFile(MultipartFile file);

    byte[] downloadFile(String filename);

    String deleteFile(String filename);

    List<String> listAllFiles();
}
