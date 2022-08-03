package com.project.indytskyi.TripsService.controllers;

import com.project.indytskyi.TripsService.dto.TripActivationDTO;
import com.project.indytskyi.TripsService.dto.TripStartDTO;
import com.project.indytskyi.TripsService.models.TrackEntity;
import com.project.indytskyi.TripsService.models.TrafficOrderEntity;
import com.project.indytskyi.TripsService.services.TrackService;
import com.project.indytskyi.TripsService.services.TrafficOrderService;
import com.project.indytskyi.TripsService.util.ErrorResponse;
import com.project.indytskyi.TripsService.util.StartOrderNotCreatedException;
import com.project.indytskyi.TripsService.util.TrafficNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public TrafficOrderEntity getTrafficOrder(@PathVariable("id") long id) {
        log.warn("Show traffic order by id = {}", id);
        return trafficOrderService.findOne(id);
    }

    /**
     * Controller where you start your work
     * initialization of traffic order and create start track
     *
     * @param tripActivation
     * @param bindingResult
     * @return
     */
    @PostMapping("/start")
    public ResponseEntity<TripStartDTO> save(@RequestBody @Valid TripActivationDTO tripActivation,
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

        return ResponseEntity.ok(createTripStartDTO(trafficOrder, track));
    }


    /**
     * Exception (if we want to find special traffic order but, the order with this id does not exist)
     *
     * @param e
     * @return
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
     *
     * @param e
     * @return
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
     *
     * @param trafficOrderEntity
     * @param trackEntity
     * @return TripStartDTO
     */
    private TripStartDTO createTripStartDTO(TrafficOrderEntity trafficOrderEntity, TrackEntity trackEntity) {
        TripStartDTO tripStartDTO = modelMapper.map(trackEntity, TripStartDTO.class);
        tripStartDTO.setOwnerId(trafficOrderEntity.getId());
        tripStartDTO.setTrackId(trackEntity.getId());
        tripStartDTO.setCarId(trafficOrderEntity.getCarId());
        tripStartDTO.setUserId(trafficOrderEntity.getUserId());
        tripStartDTO.setActivationTime(trafficOrderEntity.getActivationTime());
        tripStartDTO.setBalance(trafficOrderEntity.getBalance());
        tripStartDTO.setStatus(trafficOrderEntity.getStatus());
        tripStartDTO.setStatusPaid(trafficOrderEntity.getStatusPaid());
        return tripStartDTO;
    }


}
