package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;

public interface UserService {

    ValidateUserResponseDto validateToken(String token);

}
