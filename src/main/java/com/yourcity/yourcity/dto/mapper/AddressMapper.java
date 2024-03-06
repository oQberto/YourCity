package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.address.AddressDto;
import com.yourcity.yourcity.model.entity.Address;
import org.mapstruct.*;

import static com.yourcity.yourcity.dto.address.AddressDto.Fields.*;
import static com.yourcity.yourcity.model.entity.Address.Fields.*;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = SPRING,
        uses = {
                CountryMapper.class,
                CityMapper.class,
                StreetMapper.class
        }
)
public interface AddressMapper {

        @Mappings({
                @Mapping(target = country, source = countryDto),
                @Mapping(target = city, source = cityDto),
                @Mapping(target = street, source = streetDto)
        })
        @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
        Address mapToAddress(AddressDto dto);

        @Mappings({
                @Mapping(target = countryDto, source = country),
                @Mapping(target = cityDto, source = city),
                @Mapping(target = streetDto, source = street)
        })
        AddressDto mapToAddressDto(Address entity);

        @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
        Address updateAddress(AddressDto dto,
                                 @MappingTarget Address entity);
}