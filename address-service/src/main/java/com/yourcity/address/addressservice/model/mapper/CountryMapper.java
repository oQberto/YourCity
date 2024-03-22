package com.yourcity.address.addressservice.model.mapper;

import com.yourcity.address.addressservice.model.dto.country.CountryDto;
import com.yourcity.domain.domain.model.entity.Country;
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
