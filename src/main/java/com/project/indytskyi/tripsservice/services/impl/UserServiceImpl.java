package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserRequestDto;
import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String BEARER_TOKEN_START = "Bearer ";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private final WebClient userWebClient;

    @Override
    public ValidateUserResponseDto validateUserToken(String token) {
        return userWebClient.post()
                .uri("validate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromValue(new ValidateUserRequestDto(BEARER_TOKEN_START + token)))
                .header("Authorization", BEARER_TOKEN_START + token)
                .retrieve()
                .bodyToMono(ValidateUserResponseDto.class)
                .block();
    }

    @Override
    public void checkIfTheConsumerIsAdmin(String token) {

        if (!validateUserToken(token).getRoles().contains(ROLE_ADMIN)) {
            throw new ApiValidationException(List.of(new ErrorResponse(
                    "Role - ADMIN",
                    "You do not have access to this part. Contact support"
            )));
        }
    }

    @Override
    public void checkIfTheConsumerIsUser(String token) {
        if (!validateUserToken(token).getRoles().contains(ROLE_USER)) {
            throw new ApiValidationException(List.of(new ErrorResponse(
                    "Role",
                    "You do not have access to this part."
                            + " log in to your account to start the trip"
            )));
        }
    }

}
