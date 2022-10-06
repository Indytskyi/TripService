package com.project.indytskyi.tripsservice.dto.user;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
public class ValidateUserResponseDto {
    private Long userId;
    private List<String> roles;

}
