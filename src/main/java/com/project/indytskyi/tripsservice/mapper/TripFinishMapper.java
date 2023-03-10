package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.TripFinishDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for converting data from
 * TrafficOrderEntity and TrackEntity
 * to TripFinishDto
 */
@Mapper(componentModel = "spring")
public interface TripFinishMapper {

    TripFinishDto toTripFinishDto(TrafficOrderEntity trafficOrder,
                                  TrackEntity trackEntity);

}
