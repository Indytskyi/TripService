package com.project.indytskyi.TripsService.controllers;

import com.project.indytskyi.TripsService.dto.CurrentCoordinatesDTO;
import com.project.indytskyi.TripsService.models.TrackEntity;
import com.project.indytskyi.TripsService.services.TrackService;
import com.project.indytskyi.TripsService.util.CurrentTrackNotCreatedException;
import com.project.indytskyi.TripsService.util.ErrorResponse;
import com.project.indytskyi.TripsService.util.TrackNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("trip/track")
@AllArgsConstructor
@Slf4j
public class TrackController {
    private final TrackService trackService;

    /**
     * Controller where we want to find special track by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public TrackEntity getTrackOrder(@PathVariable("id") long id) {
        log.warn("Show track by id");
        return trackService.findOne(id);
    }


    /**
     * Controller where you get json with current coordinates
     *  create current track
     * @param currentCoordinates
     * @param bindingResult
     * @return TrackEntity
     */
    @PostMapping("/current")
    public ResponseEntity<TrackEntity> getCurrentCoordinates(@RequestBody @Valid CurrentCoordinatesDTO currentCoordinates,
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
     * @param e
     * @return ErrorResponse
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
     * @param e
     * @return ErrorResponse
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
