package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.country.CountryDto;
import com.yourcity.yourcity.model.entity.Country;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public interface CountryMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Country mapToCountry(CountryDto dto);

    CountryDto mapToCountryDto(Country entity);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Country updateCountry(CountryDto dto,
                          @MappingTarget Country entity);
}
