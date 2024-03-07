package com.yourcity.yourcity.dto.mapper;

import com.yourcity.yourcity.dto.event.EventDto;
import com.yourcity.yourcity.model.entity.Event;
import org.mapstruct.*;

import static com.yourcity.yourcity.dto.event.EventDto.Fields.addressDto;
import static com.yourcity.yourcity.dto.event.EventDto.Fields.ownerDto;
import static com.yourcity.yourcity.model.entity.Event.Fields.address;
import static com.yourcity.yourcity.model.entity.Event.Fields.owner;
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
            @Mapping(target = owner, source = ownerDto),
            @Mapping(target = address, source = addressDto)
    })
    Event mapToEvent(EventDto dto);

    @Mappings({
            @Mapping(target = ownerDto, source = owner),
            @Mapping(target = addressDto, source = address)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    EventDto mapToEventDto(Event entity);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Event updateEvent(EventDto dto, @MappingTarget Event event);
}
