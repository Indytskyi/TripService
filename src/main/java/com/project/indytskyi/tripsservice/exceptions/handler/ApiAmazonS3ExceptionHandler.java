package com.project.indytskyi.tripsservice.exceptions.handler;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiAmazonS3ExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AmazonS3Exception e) {
        log.error("There is no picture with this path, throw = {}", e.getClass());
        ErrorResponse errorResponse = new ErrorResponse("path",
                "There is no picture with this path");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
