package com.yourcity.event.eventservice.model.mapper;

import com.yourcity.address.addressservice.model.mapper.AddressMapper;
import com.yourcity.domain.entity.Event;
import com.yourcity.event.eventservice.model.dto.event.EventDto;
import com.yourcity.user.userservice.model.mapper.UserMapper;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = SPRING,
        uses = {
                UserMapper.class,
                AddressMapper.class
        }
)
public interface EventMapper {

    @Mappings({
            @Mapping(target = "owner", source = "ownerDto"),
            @Mapping(target = "address", source = "addressDto")
    })
    Event mapToEvent(EventDto dto);

    @Mappings({
            @Mapping(target = "ownerDto", source = "owner"),
            @Mapping(target = "addressDto", source = "address")
    })
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    EventDto mapToEventDto(Event entity);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Event updateEvent(EventDto dto, @MappingTarget Event event);
}
