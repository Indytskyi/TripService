package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for covert data from TrafficOrderEntity
 * and trackEntity to LastCarCoordinatesDto
 */
@Mapper(componentModel = "spring")
public interface StartMapper {
     TripStartDto toStartDto(TrafficOrderEntity trafficOrderEntity, TrackEntity trackEntity);

}
