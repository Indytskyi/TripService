package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenValidation {

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    public void checkIfTheConsumerIsAdmin(ValidateUserResponseDto responseDto) {

        if (!responseDto.getRoles().contains(ROLE_ADMIN)) {
            throw new ApiValidationException(List.of(new ErrorResponse(
                    "Role - ADMIN",
                    "You do not have access to this part. Contact support"
            )));
        }
    }

    public void checkIfTheConsumerIsOrdinary(ValidateUserResponseDto responseDto,
                                             long userId) {
        if (!(responseDto.getRoles().contains(ROLE_USER) && responseDto.getUserId() == userId)
                && !responseDto.getRoles().contains(ROLE_ADMIN)) {
            throw new ApiValidationException(List.of(new ErrorResponse(
                    "Role",
                    "You do not have access to this part."
                            + " log in to your account to start the trip"
            )));
        }
    }
}
