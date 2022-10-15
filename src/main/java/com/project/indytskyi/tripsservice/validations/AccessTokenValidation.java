package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.exceptions.AccessRequestException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenValidation {

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    private final TrafficOrderService trafficOrderService;

    public void checkIfTheConsumerIsAdmin(ValidateUserResponseDto responseDto) {

        if (!responseDto.getRoles().contains(ROLE_ADMIN)) {
            throw new AccessRequestException(new ErrorResponse(
                    "Role - ADMIN",
                    "You do not have access to this part. Contact support"
            ));
        }
    }

    public void checkIfTheConsumerIsOrdinary(ValidateUserResponseDto responseDto,
                                             long tripId) {
        TrafficOrderEntity trafficOrder = trafficOrderService.findTrafficOrderById(tripId);
        if (!(responseDto.getRoles().contains(ROLE_USER)
                && responseDto.getUserId() == trafficOrder.getUserId())
                && !responseDto.getRoles().contains(ROLE_ADMIN)) {
            throw new AccessRequestException(new ErrorResponse(
                    "Role",
                    "You do not have access to this part."
                            + " log in to your account to start the trip"
            ));
        }
    }
}
