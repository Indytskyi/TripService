package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrafficOrderValidation {

    private final TrafficsRepository trafficsRepository;

    public void validateActiveCountOfTrafficOrders(long userId) {
        Optional<TrafficOrderEntity> trafficOrderEntityOptional = trafficsRepository
                .findFirstByUserIdOrderByIdDesc(userId);
        if (trafficOrderEntityOptional.isPresent()) {
            if (trafficOrderEntityOptional.get().getStatus().equals("IN_ORDER")) {
                List<ErrorResponse> exception = List.of(new ErrorResponse("userId",
                        "You have already started the trip."
                                + "Finish the previous one to start a new one")
                );

                throw new ApiValidationException(exception);
            }
        }
    }
}
