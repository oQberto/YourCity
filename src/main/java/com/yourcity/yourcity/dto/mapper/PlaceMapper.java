package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.place.PlaceDto;
import com.yourcity.yourcity.model.entity.Place;
import org.mapstruct.*;

import static com.yourcity.yourcity.dto.place.PlaceDto.Fields.addressDto;
import static com.yourcity.yourcity.model.entity.Place.Fields.address;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, uses = {AddressMapper.class})
public interface PlaceMapper {

    @Mapping(target = address, source = addressDto)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Place mapToPlace(PlaceDto dto);

    @Mapping(target = addressDto, source = address)
    PlaceDto mapToPlaceDto(Place entity);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Place updatePlace(PlaceDto dto,
                         @MappingTarget Place place);
}
