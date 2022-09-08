package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import com.project.indytskyi.tripsservice.services.ImageS3Service;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trip/image")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final TrafficOrderService trafficOrderService;
    private final ImageS3Service imageS3Service;

    @ApiOperation(value = "Find all path of images by traffic order id ")
    @ApiResponse(code = 400, message = "Invalid traffic order Id")
    @GetMapping("/{id}")
    public ResponseEntity<List<String>> getTrafficOrderImages(
            @PathVariable("id") long trafficOrderId) {
        log.warn("Show traffic order by id = {}", trafficOrderId);
        List<ImagesEntity> images = trafficOrderService
                .findOne(trafficOrderId)
                .getImages();
        List<String> paths = images.stream().map(ImagesEntity::getImage).toList();
        return ResponseEntity
                .ok(paths);
    }

    @ApiOperation(value = "Download file by path of this file")
    @ApiResponse(code = 400, message = "Invalid path")
    @GetMapping
    public ResponseEntity<byte[]> downloadImage(@RequestParam("path") String path) {
        log.warn("Path of file that we download = {}", path);

        return ResponseEntity
                .ok(imageS3Service.downloadFile(path));
    }

}
