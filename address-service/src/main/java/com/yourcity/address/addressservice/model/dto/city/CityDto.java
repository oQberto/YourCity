package com.yourcity.address.addressservice.model.dto.city;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CityDto {
    Long id;
    String name;
    String description;
}
