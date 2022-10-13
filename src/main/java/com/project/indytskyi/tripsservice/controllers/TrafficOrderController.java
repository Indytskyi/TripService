package com.project.indytskyi.tripsservice.controllers;

import com.project.indytskyi.tripsservice.annotation.PictureValidation;
import com.project.indytskyi.tripsservice.dto.StatusDto;
import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.services.TripService;
import com.project.indytskyi.tripsservice.services.UserService;
import com.project.indytskyi.tripsservice.validations.AccessTokenValidation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
@Slf4j
public class TrafficOrderController {

    private final TripService tripService;
    private final UserService userService;
    private final AccessTokenValidation accessTokenValidation;

    /**
     * Controller where you start your work
     * initialization of traffic order and create start track
     */
    @ApiOperation(value = "Create traffic order and start tracking last coordinates of the car")
    @ApiResponse(code = 400, message = "Invalid some data")
    @PostMapping
    public ResponseEntity<TripStartDto> save(@RequestBody @Valid TripActivationDto tripActivation,
                                             @RequestHeader("Authorization") String token) {

        if (tripActivation.getUserId() != userService.validateToken(token).getUserId()) {
            throw new ApiValidationException(List.of(new ErrorResponse(
                    "Role",
                    "You do not have access to this part."
                            + " log in to your account to start the trip"
            )));
        }

        log.info("Create new traffic order and start track");

        return ResponseEntity.ok(tripService.startTrip(tripActivation, token));
    }

    /**
     * Controller where we want to find special traffic order by id
     */
    @ApiOperation(value = "Find special traffic order by id")
    @ApiResponse(code = 400, message = "Invalid traffic order Id")
    @GetMapping("/{id}")
    public ResponseEntity<TrafficOrderDto> getTrafficOrder(
            @PathVariable("id") long id,
            @RequestHeader("Authorization") String token) {

        accessTokenValidation.checkIfTheConsumerIsAdmin(userService.validateToken(token));

        log.info("Show  order by id = {}", id);
        return ResponseEntity
                .ok(tripService.getTripById(id));
    }

    /**
     * Controller where you stop your order but don`t finish
     */
    @ApiOperation(value = "Stop traffic order")
    @ApiResponse(code = 400, message = "Invalid traffic order Id")
    @PatchMapping("{id}/status")
    public ResponseEntity<TrafficOrderDto> changeTripStatus(
            @PathVariable("id") long trafficOrderId,
            @RequestBody StatusDto statusDto,
            @RequestHeader("Authorization") String token) {

        accessTokenValidation.checkIfTheConsumerIsOrdinary(userService.validateToken(token),
                trafficOrderId);

        log.info("Stop traffic order by id = {}", trafficOrderId);

        return ResponseEntity.ok(tripService.changeTripStatus(trafficOrderId, statusDto));
    }

    /**
     * Controller where you finish your order and send json to another service
     */
    @ApiOperation(value = "Put images to database, "
            + "calculate trip payment and  return responses to user")
    @PostMapping("/{id}/photos")
    @PictureValidation
    public ResponseEntity<TripFinishDto> finish(
            @PathVariable("id") long trafficOrderId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String token) {

        accessTokenValidation.checkIfTheConsumerIsOrdinary(userService.validateToken(token),
                trafficOrderId);

        log.info("Finish traffic order by id = {}", trafficOrderId);

        return ResponseEntity.ok(tripService.finishTrip(trafficOrderId, files));
    }

}
