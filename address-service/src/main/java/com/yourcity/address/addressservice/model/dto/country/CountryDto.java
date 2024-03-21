package com.yourcity.address.addressservice.model.dto.country;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CountryDto {
    Long id;
    String name;
    String description;
}
