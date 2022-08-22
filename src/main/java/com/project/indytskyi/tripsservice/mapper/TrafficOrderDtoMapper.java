package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.TrafficOrderDto;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for converting data from
 * TrafficOrderEntity
 * to TrafficOrderDto
 */
@Mapper(componentModel = "spring")
public interface TrafficOrderDtoMapper {

    TrafficOrderDto toTrafficOrderDto(TrafficOrderEntity trafficOrderEntity);

}
