package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.annotation.PictureValidation;
import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.mapper.TrafficOrderDtoMapper;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import com.project.indytskyi.tripsservice.services.TripService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
@Slf4j
public class TrafficOrderController {
    private final TrafficOrderService trafficOrderService;
    private final TrafficOrderDtoMapper trafficOrderDtoMapper;

    private final TripService tripService;

    /**
     * Controller where you start your work
     * initialization of traffic order and create start track
     */
    @ApiOperation(value = "Create traffic order and start tracking last coordinates of the car")
    @ApiResponse(code = 400, message = "Invalid some data")
    @PostMapping
    public ResponseEntity<TripStartDto> save(@RequestBody @Valid TripActivationDto tripActivation) {
        log.info("Create new traffic order and start track");

        return ResponseEntity.ok(tripService.startTrip(tripActivation));
    }

    /**
     * Controller where we want to find special traffic order by id
     */
    @ApiOperation(value = "Find special traffic order by id")
    @ApiResponse(code = 400, message = "Invalid traffic order Id")
    @GetMapping("/{id}")
    public ResponseEntity<TrafficOrderDto> getTrafficOrder(@PathVariable("id") long id) {
        log.info("Show traffic order by id = {}", id);
        return ResponseEntity
                .ok(trafficOrderDtoMapper
                        .toTrafficOrderDto(trafficOrderService.findOne(id)));
    }

    /**
     * Controller where you stop your order but don`t finish
     */
    @ApiOperation(value = "Stop traffic order")
    @ApiResponse(code = 400, message = "Invalid traffic order Id")
    @PutMapping("{id}")
    public ResponseEntity<Map<String, String>> stop(@PathVariable("id") long trafficOrderId) {
        log.info("Stop traffic order by id = {}", trafficOrderId);
        return ResponseEntity.ok(trafficOrderService.stopOrder(trafficOrderId));
    }

    /**
     * Controller where you finish your order and send json to another service
     */
    @ApiOperation(value = "Put images to database, "
            + "calculate trip payment and  return responses to user")
    @PostMapping("/{id}")
    @PictureValidation
    public ResponseEntity<TripFinishDto> finish(@PathVariable("id") long trafficOrderId,
                                                @RequestParam("files") List<MultipartFile> files) {
        log.info("Finish traffic order by id = {}", trafficOrderId);

        return ResponseEntity.ok(tripService.finishTrip(trafficOrderId, files));
    }

}
