package com.project.indytskyi.tripsservice.services;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;

public interface UserService {

    ValidateUserResponseDto validateUserToken(String token);

    void checkIfTheConsumerIsAdmin(String token);

    void checkIfTheConsumerIsUser(String token);

}
