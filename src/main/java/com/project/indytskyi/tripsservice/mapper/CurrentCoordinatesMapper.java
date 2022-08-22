package com.project.indytskyi.tripsservice.mapper;

import com.project.indytskyi.tripsservice.dto.CurrentCoordinatesDto;
import com.project.indytskyi.tripsservice.dto.TripActivationDto;
import lombok.Generated;
import org.mapstruct.Mapper;

@Generated
@Mapper(componentModel = "spring")
public interface CurrentCoordinatesMapper {

    CurrentCoordinatesDto toCurrentCoordinates(TripActivationDto tripActivationDto);

}
