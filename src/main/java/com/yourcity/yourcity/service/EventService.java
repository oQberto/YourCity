package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.event.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public interface EventService {

    EventDto getEventById(Long id);

    EventDto getEventByName(String name);

    List<EventDto> getEventsByEventTime(OffsetDateTime time, Pageable pageable);

    List<EventDto> getEventByOwnerName(String name, Pageable pageable);

    List<EventDto> getEventsByAddress(String address, Pageable pageable);

    EventDto createEvent(EventDto dto);

    EventDto updateEvent(EventDto dto);

    void deleteEvent(Long id);
}
