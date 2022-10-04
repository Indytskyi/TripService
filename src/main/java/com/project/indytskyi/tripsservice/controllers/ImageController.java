package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.services.ImageS3Service;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trip/")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final TrafficOrderService trafficOrderService;
    private final ImageS3Service imageS3Service;

    @ApiOperation(value = "Download file by path of this file")
    @ApiResponse(code = 400, message = "Invalid path")
    @SneakyThrows
    @GetMapping("/image")
    public HttpEntity<byte[]> downloadImage(@RequestParam("path") String path) {
        log.info("Uploading image by path = {}", path);
        MediaType contentType = path.endsWith("jpg") ? MediaType.IMAGE_JPEG
                                    : MediaType.IMAGE_PNG;
        byte[] image = imageS3Service.downloadFile(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setContentLength(image.length);

        return new HttpEntity<>(image, headers);
    }

}
