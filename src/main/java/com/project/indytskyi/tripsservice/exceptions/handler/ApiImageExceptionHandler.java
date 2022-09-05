package com.project.indytskyi.tripsservice.exceptions.handler;

import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiImageExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<List<ErrorResponse>> handleException(
            ApiValidationImageException e) {
        return new ResponseEntity<>(e.getErrorResponses(), HttpStatus.BAD_REQUEST);
    }

}
