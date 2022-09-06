package com.project.indytskyi.tripsservice.exceptions;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public class ApiValidationImageException extends RuntimeException {

    private List<ErrorResponse> errorResponses;

}
