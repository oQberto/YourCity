package com.yourcity.event.eventservice.service.impl;

import com.yourcity.event.eventservice.model.dto.event.EventDto;
import com.yourcity.event.eventservice.model.mapper.EventMapper;
import com.yourcity.event.eventservice.repository.EventRepository;
import com.yourcity.event.eventservice.service.EventService;
import com.yourcity.user.userservice.service.exception.EntityCreationException;
import com.yourcity.user.userservice.service.exception.EntityUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class EventServiceImpl implements EventService {
    public static final String GET_EVENT_BY_ID = "Couldn't find an event with id \"%d\".";
    public static final String GET_EVENT_BY_NAME = "Couldn't find an event with name \"%s\".";
    public static final String CREATE_EVENT = "Couldn't create an event.";
    public static final String UPDATE_EVENT = "Couldn't update an event.";

    EventRepository eventRepository;
    EventMapper eventMapper;

    @Override
    public EventDto getEventById(Long id) {
        return eventRepository
                .findById(id)
                .map(eventMapper::mapToEventDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_EVENT_BY_ID, id)
                        ));
    }

    @Override
    public EventDto getEventByName(String name) {
        return eventRepository
                .findByName(name)
                .map(eventMapper::mapToEventDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_EVENT_BY_NAME, name)
                        ));
    }

    @Override
    public List<EventDto> getEventsByEventTime(LocalDateTime time, Pageable pageable) {
        return eventRepository
                .findAllByEventTime(time.truncatedTo(ChronoUnit.MINUTES), pageable)
                .stream()
                .map(eventMapper::mapToEventDto)
                .toList();
    }

    @Override
    public List<EventDto> getEventByOwnerName(String name, Pageable pageable) {
        return eventRepository
                .findAllByOwnerUsername(name, pageable)
                .stream()
                .map(eventMapper::mapToEventDto)
                .toList();
    }

    @Override
    @Deprecated
    public List<EventDto> getEventsByAddress(String address, Pageable pageable) {
        //TODO: implement method
        return null;
    }

    @Override
    @Transactional
    public EventDto createEvent(EventDto dto) {
        return Optional.of(dto)
                .map(eventMapper::mapToEvent)
                .map(eventRepository::saveAndFlush)
                .map(eventMapper::mapToEventDto)
                .orElseThrow(() -> new EntityCreationException(CREATE_EVENT));
    }

    @Override
    @Transactional
    public EventDto updateEvent(EventDto dto) {
        return eventRepository
                .findById(dto.getId())
                .map(event -> eventMapper.updateEvent(dto, event))
                .map(eventMapper::mapToEventDto)
                .orElseThrow(() -> new EntityUpdateException(UPDATE_EVENT));
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        eventRepository
                .findById(id)
                .ifPresent(eventRepository::delete);
    }
}
