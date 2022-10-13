package com.project.indytskyi.tripsservice.validations;

import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.TRAFFIC_ORDER_USER_ID;
import static com.project.indytskyi.tripsservice.factory.model.TrafficOrderFactory.createTrafficOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.project.indytskyi.tripsservice.dto.user.ValidateUserResponseDto;
import com.project.indytskyi.tripsservice.exceptions.ApiValidationException;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import com.project.indytskyi.tripsservice.services.TrafficOrderService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccessTokenValidationTest {

    @InjectMocks
    private AccessTokenValidation accessTokenValidation;

    @Mock
    private TrafficOrderService trafficOrderService;

    @Test
    void checkIfTheConsumerIsAdminThrowException() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(3L);
        responseDto.setRoles(List.of("USER"));

        try {
            //WHEN
            accessTokenValidation.checkIfTheConsumerIsAdmin(responseDto);

        } catch (Exception e) {
            //THEN
            assertEquals(e.getClass(), ApiValidationException.class);
        }
    }

    @Test
    void checkIfTheConsumerIsAdminDoNotThrowException() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(3L);
        responseDto.setRoles(List.of("ADMIN"));

        //WHEN
        accessTokenValidation.checkIfTheConsumerIsAdmin(responseDto);

        //THEN
    }


    @Test
    void checkIfTheConsumerIsOrdinaryDoNotGetRoleUserOrAdmin() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(22L);
        responseDto.setRoles(List.of("CAR_OWNER"));
        TrafficOrderEntity trafficOrder = createTrafficOrder();

        //WHEN
        when(trafficOrderService.findTrafficOrderById(TRAFFIC_ORDER_ID)).thenReturn(trafficOrder);
        try {
            accessTokenValidation.checkIfTheConsumerIsOrdinary(responseDto, TRAFFIC_ORDER_ID);

        } catch (Exception e) {
            //THEN
            assertEquals(e.getClass(), ApiValidationException.class);
        }
    }


    @Test
    void checkIfTheConsumerIsOrdinaryGetRoleUserButWithIncorrectUserId() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(23L);
        responseDto.setRoles(List.of("USER"));
        TrafficOrderEntity trafficOrder = createTrafficOrder();

        //WHEN
        when(trafficOrderService.findTrafficOrderById(TRAFFIC_ORDER_ID)).thenReturn(trafficOrder);
        try {
            accessTokenValidation.checkIfTheConsumerIsOrdinary(responseDto, TRAFFIC_ORDER_ID);

        } catch (Exception e) {
            //THEN
            assertEquals(e.getClass(), ApiValidationException.class);
        }
    }

    @Test
    void checkIfTheConsumerIsOrdinaryGetRoleAdmin() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(23L);
        responseDto.setRoles(List.of("ADMIN"));

        //WHEN
        accessTokenValidation.checkIfTheConsumerIsOrdinary(responseDto, TRAFFIC_ORDER_USER_ID);
    }

    @Test
    void checkIfTheConsumerIsOrdinaryGetRoleUserWithCorrectUserId() {
        //GIVEN
        ValidateUserResponseDto responseDto = new ValidateUserResponseDto();
        responseDto.setUserId(22L);
        responseDto.setRoles(List.of("USER"));
        when(trafficOrderService.findTrafficOrderById(TRAFFIC_ORDER_ID)).thenReturn(createTrafficOrder());

        //WHEN
        accessTokenValidation.checkIfTheConsumerIsOrdinary(responseDto, TRAFFIC_ORDER_USER_ID);
    }


}