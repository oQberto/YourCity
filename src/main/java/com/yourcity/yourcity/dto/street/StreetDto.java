package com.yourcity.yourcity.dto.street;

import com.yourcity.yourcity.model.entity.enums.Type;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StreetDto {
    Long id;
    Type type;
    String name;
}
