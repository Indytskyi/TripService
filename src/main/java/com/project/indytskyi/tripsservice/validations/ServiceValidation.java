package com.project.indytskyi.tripsservice.validations;

import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.ErrorResponse;
import com.project.indytskyi.tripsservice.exceptions.enums.StatusException;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import com.project.indytskyi.tripsservice.util.enums.Status;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceValidation {

    private final TrafficsRepository trafficsRepository;

    public void validateActiveCountOfTrafficOrders(long userId) {

        log.info("check if there are any unfinished trips from usesId = {}", userId);

        Optional<TrafficOrderEntity> trafficOrderEntityOptional = trafficsRepository
                .findFirstByUserIdOrderByIdDesc(userId);
        if (trafficOrderEntityOptional.isPresent()
                && !trafficOrderEntityOptional.get()
                .getStatus().equals(Status.FINISH.name())) {
            List<ErrorResponse> exception = List.of(new ErrorResponse("userId: " + userId,
                    "You have already started the trip."
                            + "Finish the previous one to start a new one")
            );

            log.error("the previous trip don`t finish ");

            throw new ApiValidationException(exception);

        }
    }

    public void validationForStatusChange(String desiredStatus, String currentStatus) {

        if (!desiredStatus.equals(Status.IN_ORDER.name())
                && !desiredStatus.equals(Status.STOP.name())) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    "Incorrect data entry: '"
                            + desiredStatus
                            + " in status; "
                            + "Possible values: "
                            + "[IN_ORDER, STOP]")));
        }

        if (currentStatus.equals("FINISH")) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    StatusException.TRIP_COMPLETED_EXCEPTION.getException())));
        }

    }

    public void validateStatusAccess(String currentStatus, String expectedStatus,
                                     String exceptionMessage) {
        if (!currentStatus.equals(expectedStatus)) {
            throw new ApiValidationException(List.of(new ErrorResponse("status",
                    exceptionMessage)));
        }
    }

}
