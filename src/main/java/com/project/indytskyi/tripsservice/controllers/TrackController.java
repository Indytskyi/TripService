package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.dto.AllTracksDto;
import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TrackDto;
import com.project.indytskyi.tripsservice.mapper.TrackDtoMapper;
import com.project.indytskyi.tripsservice.services.TrackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for tracks.
 */
@RestController
@RequestMapping("trip/")
@Slf4j
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;
    private final TrackDtoMapper trackDtoMapper;

    /**
     * Controller where we want to find special track by id
     */
    @ApiOperation(value = "Find special track by id")
    @ApiResponse(code = 400, message = "Invalid track Id")
    @GetMapping("track/{id}")
    public ResponseEntity<TrackDto> getTrack(@PathVariable("id") long id,
                                             @RequestHeader("Authorization") String token) {
        log.info("Show track by id = {}", id);

        return ResponseEntity.ok(trackDtoMapper
                .toTrackDto(trackService.findOne(id, token)));
    }

    @ApiOperation(value = "Find all tracks by id")
    @ApiResponse(code = 400, message = "Invalid order Id")
    @GetMapping("{id}/tracks")
    public ResponseEntity<AllTracksDto> getAllTracksByOrderId(
            @PathVariable("id") long id,
            @RequestHeader("Authorization") String token) {

        log.info("Show all tracks  by order id = {}", id);
        return ResponseEntity.ok(trackService.getListOfAllCoordinates(id, token));
    }

    /**
     * Controller where you get json with current coordinates
     * create current track
     */
    @ApiOperation("Get json with current coordinates and create current track")
    @ApiResponse(code = 400, message = "Invalid some data")
    @PostMapping("track")
    public ResponseEntity<TrackDto> getCurrentCoordinates(
            @RequestBody @Valid CurrentCoordinatesDto currentCoordinates,
            @RequestHeader("Authorization") String token) {

        log.info("Save current coordinates with latitude = {}, longitude = {}",
                currentCoordinates.getLatitude(),
                currentCoordinates.getLongitude());

        return ResponseEntity.ok(trackDtoMapper
                .toTrackDto(trackService
                        .saveTrack(currentCoordinates, token)));
    }

}
