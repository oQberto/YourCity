package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.city.CityDto;
import com.yourcity.yourcity.model.entity.City;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City mapToCity(CityDto dto);

    CityDto mapToCityDto(City entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City updateCity(CityDto dto,
                    @MappingTarget City city);
}
