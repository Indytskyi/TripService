package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.TrackDto;
import com.project.indytskyi.tripsservice.models.TrackEntity;
import lombok.Generated;
import org.mapstruct.Mapper;

/**
 * Mapper for converting data from
 * TrackEntity
 * to TrackDto
 */
@Generated
@Mapper(componentModel = "spring")
public interface TrackDtoMapper {

    TrackDto toTrackDto(TrackEntity trackEntity);

}
