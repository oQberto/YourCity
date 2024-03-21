package com.yourcity.address.addressservice.model.mapper;

import com.yourcity.address.addressservice.model.dto.city.CityDto;
import com.yourcity.domain.entity.City;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public interface CityMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    City mapToCity(CityDto dto);

    CityDto mapToCityDto(City entity);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    City updateCity(CityDto dto,
                    @MappingTarget City city);
}
