package com.project.indytskyi.tripsservice.validations;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_USER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.exceptions.enums.StatusException;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.repositories.TrafficsRepository;
import com.project.indytskyi.tripsservice.util.enums.Status;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServiceValidationTest {

    @InjectMocks
    private ServiceValidation serviceValidation;

    @Mock
    private TrafficsRepository repository;

    @Test
    @DisplayName("check if the user has not started a trip before")
    void validateActiveCountOfTrafficOrdersWithoutTripsBefore() {
        //GIVEN
        Optional<TrafficOrderEntity> trafficOrderEntityOptional = Optional.empty();

        //WHEN
        when(repository.findFirstByUserIdOrderByIdDesc(TRAFFIC_ORDER_USER_ID))
                .thenReturn(trafficOrderEntityOptional);

        try {
            serviceValidation.validateActiveCountOfTrafficOrders(TRAFFIC_ORDER_USER_ID);
        } catch (Exception e) {
            assertEquals(e.getClass(), ApiValidationException.class);
        }
    }

    @Test
    @DisplayName("check if the user has started a trip before and hasn't finished")
    void validateActiveCountOfTrafficOrdersWithOpenTrip() {
        //GIVEN
        Optional<TrafficOrderEntity> trafficOrderEntityOptional = Optional.of(createTrafficOrder());

        //WHEN
        when(repository.findFirstByUserIdOrderByIdDesc(TRAFFIC_ORDER_USER_ID))
                .thenReturn(trafficOrderEntityOptional);

        try {
            serviceValidation.validateActiveCountOfTrafficOrders(TRAFFIC_ORDER_USER_ID);
        } catch (Exception e) {
            assertEquals(e.getClass(), ApiValidationException.class);
        }
    }

    @Test
    @DisplayName("check if the user has started a trip before and finished it")
    void validateActiveCountOfTrafficOrdersWithClosedTrip() {
        //GIVEN
        TrafficOrderEntity trafficOrder = createTrafficOrder();
        trafficOrder.setStatus(Status.FINISH.name());
        Optional<TrafficOrderEntity> trafficOrderEntityOptional = Optional.of(trafficOrder);

        //WHEN
        when(repository.findFirstByUserIdOrderByIdDesc(TRAFFIC_ORDER_USER_ID))
                .thenReturn(trafficOrderEntityOptional);

        serviceValidation.validateActiveCountOfTrafficOrders(TRAFFIC_ORDER_USER_ID);
    }

    @Test
    @DisplayName("Check for correctness of entered data when prompted about status changes" +
            "Input correct!!")
    void validationForStatusChange() {
        //GIVEN
        String desiredStatus1 = "STOP";
        String desiredStatus2 = "IN_ORDER";

        //WHEN
        serviceValidation.validationForStatusChange(desiredStatus1, desiredStatus1);
        serviceValidation.validationForStatusChange(desiredStatus2, desiredStatus2);

    }

    @Test
    @DisplayName("Check for correctness of entered data when prompted about status changes" +
            "Input incorrect!! Throw ApiValidationException")
    void validationForStatusChangeWithIncorrectDesiredStatus() {
        //GIVEN
        String desiredStatus = "FINISH";
        try {
            //WHEN
            serviceValidation.validationForStatusChange(desiredStatus, Status.FINISH.name());
        } catch (Exception e) {
            //THEN
            assertEquals(e.getClass(), ApiValidationException.class);
        }

    }


    @Test
    @DisplayName("If the current status of trip is FINISH, should throw exception")
    void validationForStatusChangeWithIncorrectCurrentStatus() {
        //GIVEN
        String desiredStatus = "IN_ORDER";
        try {
            //WHEN
            serviceValidation.validationForStatusChange(desiredStatus, Status.FINISH.name());
        } catch (Exception e) {
            //THEN
            assertEquals(e.getClass(), ApiValidationException.class);
        }

    }

    @Test
    @DisplayName("Check If status expected and current correct")
    void validateStatusAccess() {
        //GIVEN
        String currentStatus = Status.IN_ORDER.name();
        String expectedStatus = Status.IN_ORDER.name();

        //WHEN
        serviceValidation.validateStatusAccess(currentStatus, expectedStatus,
                StatusException.STOPPED_CAR_EXCEPTION.getException());
    }

    @Test
    @DisplayName("Check If status expected and current incorrect")
    void validateStatusAccessWithThrowException() {
        //GIVEN
        String currentStatus = Status.IN_ORDER.name();
        String expectedStatus = Status.FINISH.name();

        try {
            //WHEN
            serviceValidation.validateStatusAccess(currentStatus, expectedStatus,
                    StatusException.STOPPED_CAR_EXCEPTION.getException());
        } catch (Exception e) {
            //THEN
            assertEquals(e.getClass(), ApiValidationException.class);
        }
    }
}