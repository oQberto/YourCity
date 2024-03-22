package com.yourcity.event.eventservice.model.dto.event;

import com.yourcity.address.addressservice.model.dto.address.AddressDto;
import com.yourcity.user.userservice.model.dto.UserRepresentationDto;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@FieldNameConstants
public class EventDto {
    Long id;
    String name;
    String description;
    LocalDateTime eventTime;
    UserRepresentationDto ownerDto;
    List<UserRepresentationDto> subscribedUsers;
    AddressDto addressDto;
}
