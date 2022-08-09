package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.exceptions.StartOrderNotCreatedException;
import com.project.indytskyi.tripsservice.exceptions.TrafficNotFoundException;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.TrackService;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
@Slf4j
public class TrafficOrderController {
    private final TrafficOrderService trafficOrderService;
    private final TrackService trackService;
    private final ModelMapper modelMapper;

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
    public ResponseEntity<TripStartDto> save(@RequestBody @Valid TripActivationDto tripActivation,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new StartOrderNotCreatedException(errorMessage.toString());
        }

        TrafficOrderEntity trafficOrder = trafficOrderService.save(tripActivation);
        TrackEntity track = trackService.createStartTrack(trafficOrder, tripActivation);

        log.info("Save new order with traffic");

        return ResponseEntity.ok(createTripStartDto(trafficOrder, track));
    }

    /**
     * Controller where you stop your order but don`t finish
     */
    @PatchMapping("/stop/{id}")
    public ResponseEntity<HttpStatus> stop(@PathVariable("id") long trafficOrderId) {
        trafficOrderService.stopOrder(trafficOrderId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Exception (if we want to find special traffic order but,
     * the order with this id does not exist)
     */
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(TrafficNotFoundException e) {
        log.error("TrafficOrder with id wasn`t found");

        ErrorResponse errorResponse = new ErrorResponse(
                "TrafficOrder with id wasn`t found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception (if json with object of Trip Activation has bad data)
     */
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(StartOrderNotCreatedException e) {
        log.error("Start order is failed (incorrect data)");
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * this method create instance of class TripStartDTO which contains all information
     * about new traffic order and start track
     * This method is used to display, after the start of the trip
     */
    private TripStartDto createTripStartDto(TrafficOrderEntity trafficOrderEntity,
                                            TrackEntity trackEntity) {
        TripStartDto tripStartDto = modelMapper.map(trackEntity, TripStartDto.class);

        tripStartDto.setOwnerId(trafficOrderEntity.getId());
        tripStartDto.setTrackId(trackEntity.getId());
        tripStartDto.setCarId(trafficOrderEntity.getCarId());
        tripStartDto.setUserId(trafficOrderEntity.getUserId());
        tripStartDto.setActivationTime(trafficOrderEntity.getActivationTime());
        tripStartDto.setBalance(trafficOrderEntity.getBalance());
        tripStartDto.setStatus(trafficOrderEntity.getStatus());
        tripStartDto.setStatusPaid(trafficOrderEntity.getStatusPaid());
        return tripStartDto;
    }

}
