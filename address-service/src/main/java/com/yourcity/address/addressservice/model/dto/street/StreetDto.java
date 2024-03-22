package com.yourcity.address.addressservice.model.dto.street;

import com.yourcity.domain.domain.model.enums.Type;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StreetDto {
    Long id;
    Type type;
    String name;
}
