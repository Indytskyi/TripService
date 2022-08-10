package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.TripStartDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StartMapper {
    StartMapper MAPPER = Mappers.getMapper(StartMapper.class);

     TripStartDto toStartDto(TrafficOrderEntity trafficOrderEntity, TrackEntity trackEntity);

}
