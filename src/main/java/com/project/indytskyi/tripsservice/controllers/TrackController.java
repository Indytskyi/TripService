package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.services.TrackService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("trip/track")
@Slf4j
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    /**
     * Controller where we want to find special track by id
     */
    @ApiOperation("Find special track by id")
    @GetMapping("/{id}")
    public TrackEntity getTrack(@PathVariable("id") long id) {
        log.warn("Show track by id = {}", id);
        return trackService.findOne(id);
    }

    /**
     * Controller where you get json with current coordinates
     * create current track
     */
    @PostMapping("/current")
    @ApiOperation("Get json with current coordinates and create current track")
    public ResponseEntity<TrackEntity> getCurrentCoordinates(
            @RequestBody @Valid CurrentCoordinatesDto currentCoordinates) {

        log.info("Save current coordinates");

        TrackEntity track = trackService.instanceTrack(currentCoordinates);
        return ResponseEntity.ok(track);
    }

}
