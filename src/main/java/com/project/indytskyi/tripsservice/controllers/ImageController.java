package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.dto.LInksToImagesDto;
import com.project.indytskyi.tripsservice.services.TripService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trip/")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final TripService tripService;

    @ApiOperation(value = "Download file by path of this file")
    @ApiResponse(code = 400, message = "Invalid path")
    @SneakyThrows
    @GetMapping("{id}/image")
    public ResponseEntity<LInksToImagesDto> downloadImage(@PathVariable("id") long trafficOrderId) {
        log.info("forming links for downloading, for the trip  = {}", trafficOrderId);

        return ResponseEntity.ok(tripService.generatingDownloadLinks(trafficOrderId));
    }

}
