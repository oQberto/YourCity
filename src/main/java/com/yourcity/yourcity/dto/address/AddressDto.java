package com.yourcity.yourcity.dto.address;

import com.yourcity.yourcity.dto.city.CityDto;
import com.yourcity.yourcity.dto.country.CountryDto;
import com.yourcity.yourcity.dto.street.StreetDto;
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
