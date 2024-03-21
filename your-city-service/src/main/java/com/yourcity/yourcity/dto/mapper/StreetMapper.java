package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.street.StreetDto;
import com.yourcity.yourcity.model.entity.Street;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public interface StreetMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Street mapToStreet(StreetDto dto);

    StreetDto mapToStreetDto(Street entity);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Street updateStreet(StreetDto dto,
                           @MappingTarget Street street);
}
