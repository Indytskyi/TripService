package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentCoordinatesMapper {

    CurrentCoordinatesDto toCurrentCoordinates(TripActivationDto tripActivationDto);

}
