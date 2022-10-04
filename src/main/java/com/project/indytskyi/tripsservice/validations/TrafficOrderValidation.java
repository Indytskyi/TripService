package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import com.project.indytskyi.tripsservice.util.Status;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrafficOrderValidation {

    private final TrafficsRepository trafficsRepository;

    public void validateActiveCountOfTrafficOrders(long userId) {

        log.info("check if there are any unfinished trips from usesId = {}", userId);

        Optional<TrafficOrderEntity> trafficOrderEntityOptional = trafficsRepository
                .findFirstByUserIdOrderByIdDesc(userId);
        if (trafficOrderEntityOptional.isPresent()
                && trafficOrderEntityOptional.get()
                .getStatus().equals(String.valueOf(Status.IN_ORDER))) {
            List<ErrorResponse> exception = List.of(new ErrorResponse("userId: " + userId,
                    "You have already started the trip."
                            + "Finish the previous one to start a new one")
            );

            log.error("the previous trip don`t finish ");

            throw new ApiValidationException(exception);

        }
    }
}
