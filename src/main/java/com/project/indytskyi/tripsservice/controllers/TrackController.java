package com.project.indytskyi.tripsservice.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.exceptions.CurrentTrackNotCreatedException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.exceptions.TrackNotFoundException;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.services.TrackService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trip/track")
@AllArgsConstructor
@Slf4j
public class TrackController {
    private final TrackService trackService;

    /**
     * Controller where we want to find special track by id
     */
    @GetMapping("/{id}")
    public TrackEntity getTrackOrder(@PathVariable("id") long id) {
        log.warn("Show track by id");
        return trackService.findOne(id);
    }

    /**
     * Controller where you get json with current coordinates
     *  create current track
     */
    @PostMapping("/current")
    public ResponseEntity<TrackEntity> getCurrentCoordinates(
            @RequestBody @Valid CurrentCoordinatesDto currentCoordinates,
                                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new CurrentTrackNotCreatedException(errorMessage.toString());
        }

        TrackEntity track = trackService.instanceTrack(currentCoordinates);
        return ResponseEntity.ok(track);
    }

    /**
     * Exception (if we want to find special track but, the track with this id does not exist)
     */
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(TrackNotFoundException e) {
        log.error("Track with id wasn`t found");
        ErrorResponse errorResponse = new ErrorResponse(
                "Track with id wasn`t found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    /**
     * Exception (if we want to get current track information but the jsom have incorrect fields)
     */
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CurrentTrackNotCreatedException e) {
        log.error("Start order is failed (incorrect data)");
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
