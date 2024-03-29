package com.yourcity.event.eventservice.service;

import com.yourcity.event.eventservice.model.dto.event.EventDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface EventService {

    EventDto getEventById(Long id);

    EventDto getEventByName(String name);

    List<EventDto> getEventsByEventTime(LocalDateTime time, Pageable pageable);

    List<EventDto> getEventByOwnerName(String name, Pageable pageable);

    List<EventDto> getEventsByAddress(String address, Pageable pageable);

    EventDto createEvent(EventDto dto);

    EventDto updateEvent(EventDto dto);

    void deleteEvent(Long id);
}
