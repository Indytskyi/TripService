package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for converting data from TripActivation to TrafficOrderEntity
 */
@Mapper(componentModel = "spring")
public interface TrafficOrderMapper {

    TrafficOrderEntity toTrafficOrderEntity(TripActivationDto tripActivationDto);
}
