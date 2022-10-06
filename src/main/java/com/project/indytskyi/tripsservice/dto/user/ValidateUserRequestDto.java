package com.project.indytskyi.tripsservice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateUserRequestDto {
    private String token;
}
