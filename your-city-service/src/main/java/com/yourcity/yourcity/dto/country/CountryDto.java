package com.yourcity.yourcity.dto.country;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CountryDto {
    Long id;
    String name;
    String description;
}
