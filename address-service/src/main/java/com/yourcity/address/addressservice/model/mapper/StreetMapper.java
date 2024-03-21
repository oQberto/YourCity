package com.yourcity.address.addressservice.model.mapper;

import com.yourcity.address.addressservice.model.dto.street.StreetDto;
import com.yourcity.domain.entity.Street;
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
