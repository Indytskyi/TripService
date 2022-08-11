package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.LastCarCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import org.mapstruct.Mapper;

/**
 * Mapper for converting data from TripActivation to LastCarCoordinatesDto
 */
@Mapper(componentModel = "spring")
public interface LastCarCoordinatesMapper {
    LastCarCoordinatesDto toLastCarCoordinatesDto(TripActivationDto tripActivationDto);

}
