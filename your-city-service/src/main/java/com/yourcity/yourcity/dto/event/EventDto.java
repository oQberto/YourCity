package com.yourcity.yourcity.dto.event;

import com.yourcity.yourcity.dto.address.AddressDto;
import com.yourcity.yourcity.dto.user.UserRepresentationDto;
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
