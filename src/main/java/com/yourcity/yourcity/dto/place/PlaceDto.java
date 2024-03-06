package com.yourcity.yourcity.dto.place;

import com.yourcity.yourcity.dto.address.AddressDto;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
public class PlaceDto {
    Long id;
    String name;
    String description;
    PlaceCategory category;
    AddressDto addressDto;
}
