package com.yourcity.event.eventservice.model.dto.place;

import com.yourcity.address.addressservice.model.dto.address.AddressDto;
import com.yourcity.domain.enums.PlaceCategory;
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
