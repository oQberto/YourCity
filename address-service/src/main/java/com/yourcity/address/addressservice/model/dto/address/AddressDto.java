package com.yourcity.address.addressservice.model.dto.address;

import com.yourcity.address.addressservice.model.dto.city.CityDto;
import com.yourcity.address.addressservice.model.dto.country.CountryDto;
import com.yourcity.address.addressservice.model.dto.street.StreetDto;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
public class AddressDto {
    Long id;
    Short buildingNumber;
    Short roomNumber;
    CountryDto countryDto;
    CityDto cityDto;
    StreetDto streetDto;
}
