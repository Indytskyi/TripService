package com.project.indytskyi.tripsservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public class AccessRequestException extends RuntimeException {
    private ErrorResponse errorResponse;
}
