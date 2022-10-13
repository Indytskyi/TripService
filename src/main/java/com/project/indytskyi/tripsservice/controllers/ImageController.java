package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.dto.LInksToImagesDto;
import com.project.indytskyi.tripsservice.services.TripService;
import com.project.indytskyi.tripsservice.services.UserService;
import com.project.indytskyi.tripsservice.validations.AccessTokenValidation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trip/")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final UserService userService;
    private final AccessTokenValidation accessTokenValidation;
    private final TripService tripService;

    @ApiOperation(value = "Download file by path of this file")
    @ApiResponse(code = 400, message = "Invalid path")
    @GetMapping("{id}/image")
    public ResponseEntity<LInksToImagesDto> downloadImage(
            @PathVariable("id") long trafficOrderId,
            @RequestHeader("Authorization") String token) {

        accessTokenValidation.checkIfTheConsumerIsAdmin(userService.validateToken(token));

        log.info("forming links for downloading, for the trip  = {}", trafficOrderId);

        return ResponseEntity.ok(tripService.generatingDownloadLinks(trafficOrderId));
    }

}
