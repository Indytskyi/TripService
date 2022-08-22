package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.TrackDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for converting data from
 * TrackEntity
 * to TrackDto
 */
@Mapper(componentModel = "spring")
public interface TrackDtoMapper {

    TrackDto toTrackDto(TrackEntity trackEntity);

}
