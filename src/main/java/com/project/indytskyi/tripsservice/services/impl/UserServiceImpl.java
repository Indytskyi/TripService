package com.project.indytskyi.tripsservice.services.impl;

import com.project.indytskyi.tripsservice.config.DiscoveryUrlConfig;
import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DiscoveryUrlConfig basicUrl;
    private final WebClient webClient;

    @Override
    public ValidateUserResponseDto validateToken(String token) {
        return webClient.get()
                .uri(basicUrl.getUserServiceUrl() + "/validate-auth-token")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(ValidateUserResponseDto.class)
                .block();
    }

}
