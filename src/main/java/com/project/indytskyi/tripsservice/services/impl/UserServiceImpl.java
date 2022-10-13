package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String BEARER_TOKEN_START = "Bearer ";

    private final WebClient userWebClient;

    @Override
    public ValidateUserResponseDto validateToken(String token) {
        return userWebClient.get()
                .uri("validate-auth-token")
                .header("Authorization", BEARER_TOKEN_START + token)
                .retrieve()
                .bodyToMono(ValidateUserResponseDto.class)
                .block();
    }

}
