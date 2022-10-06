package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserRequestDto;
import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final WebClient userWebClient;
    private final String bearerTokenStart = "Bearer ";

    @Override
    public ValidateUserResponseDto validateUserToken(String token) {
        return userWebClient.post()
                .uri("validate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromValue(new ValidateUserRequestDto(bearerTokenStart + token)))
                .header("Authorization", bearerTokenStart + token)
                .retrieve()
                .bodyToMono(ValidateUserResponseDto.class)
                .block();
    }

}
