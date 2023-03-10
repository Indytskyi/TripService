package com.project.indytskyi.tripsservice.exceptions.handler;

import com.project.indytskyi.tripsservice.exceptions.AccessRequestException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    /**
     * If we get not valid arguments.
     * @param e = object of exception
     * 
     */
    @ExceptionHandler
    private ResponseEntity<List<ErrorResponse>> handleException(MethodArgumentNotValidException e) {
        log.error("Not valid arguments, throw = {}", e.getClass());
        List<ErrorResponse> errorResponses = e.getAllErrors().stream()
                .map(a -> new ErrorResponse(Objects.requireNonNull(a.getCodes())[1]
                        .split("\\.")[1],
                        a.getDefaultMessage())).collect(Collectors.toList());

        return new ResponseEntity<>(errorResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleAccessException(AccessRequestException e) {
        return new ResponseEntity<>(e.getErrorResponse(), HttpStatus.FORBIDDEN);
    }
    
}
