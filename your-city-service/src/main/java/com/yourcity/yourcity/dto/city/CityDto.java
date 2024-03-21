package com.yourcity.yourcity.dto.city;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CityDto {
    Long id;
    String name;
    String description;
}
