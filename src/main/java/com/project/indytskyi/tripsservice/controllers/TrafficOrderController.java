package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.mapper.StartMapper;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/trip")
@AllArgsConstructor
@Slf4j
public class TrafficOrderController {
    private final TrafficOrderService trafficOrderService;
    private final TrackService trackService;
    private final StartMapper startMapper;


    /**
     * Controller where we want to find special traffic order by id
     */
    @GetMapping("/{id}")

    public TrafficOrderEntity getTrafficOrder(@PathVariable("id") long id) {
        log.warn("Show traffic order by id = {}", id);
        return trafficOrderService.findOne(id);
    }

    /**
     * Controller where you start your work
     * initialization of traffic order and create start track
     */
    @PostMapping("/start")
    public ResponseEntity<TripStartDto> save(@RequestBody @Valid TripActivationDto tripActivation) {
        log.info("Create new traffic order and start track");

        TrafficOrderEntity trafficOrder = trafficOrderService.save(tripActivation);
        TrackEntity track = trackService.createStartTrack(trafficOrder, tripActivation);

        return ResponseEntity.ok(createTripStartDto(trafficOrder, track));
    }

    /**
     * Controller where you stop your order but don`t finish
     */
    @PatchMapping("/stop/{id}")
    public ResponseEntity<HttpStatus> stop(@PathVariable("id") long trafficOrderId) {
        log.info("Stop traffic order by id = {}", trafficOrderId);
        trafficOrderService.stopOrder(trafficOrderId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    /**
     * Controller where you finish your order and send json to another service
     */
    @PutMapping("/finish/{id}")
    public  ResponseEntity<TripFinishDto> finish(@PathVariable("id") long trafficOrderId) {
        log.info("Finish traffic order by id = {}", trafficOrderId);
        return ResponseEntity.ok(trafficOrderService.finishOrder(trafficOrderId));
    }

    /**
     * this method create instance of class TripStartDTO which contains all information
     * about new traffic order and start track
     * This method is used to display, after the start of the trip
     */
    private TripStartDto createTripStartDto(TrafficOrderEntity trafficOrderEntity,
                                            TrackEntity trackEntity) {
        TripStartDto tripStartDto = startMapper
                .toStartDto(trafficOrderEntity, trackEntity);

        tripStartDto.setOwnerId(trafficOrderEntity.getId());
        tripStartDto.setTrackId(trackEntity.getId());
        return tripStartDto;
    }


}
