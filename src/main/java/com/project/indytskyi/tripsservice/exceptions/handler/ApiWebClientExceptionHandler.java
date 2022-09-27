package com.project.indytskyi.tripsservice.exceptions.handler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
@Slf4j
public class ApiWebClientExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<Map<String, String>>
            handleStatusCodeException(WebClientResponseException e) {
        log.error("Backend returned {} {} \n {}", e.getStatusCode(),
                e.getStatusCode().getReasonPhrase(),
                e.getResponseBodyAsString());

        Map<String, String> exception = new LinkedHashMap<>();
        exception.put("timestamp", String.valueOf(LocalDateTime.now()));
        exception.put("status", String.valueOf(e.getStatusCode().value()));
        exception.put("error", e.getStatusCode().getReasonPhrase());
        exception.put("path", e.getMessage()
                .substring(e.getMessage().lastIndexOf(" ")));

        return new ResponseEntity<>(exception,
                e.getStatusCode());

    }
}
