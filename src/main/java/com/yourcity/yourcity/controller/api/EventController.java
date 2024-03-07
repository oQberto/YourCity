package com.yourcity.yourcity.controller.api;

import com.yourcity.yourcity.dto.event.EventDto;
import com.yourcity.yourcity.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class EventController {
    public static final String GET_EVENT_BY_ID = "/api/v1/events/{id}";
    public static final String GET_EVENT_BY_NAME = "/api/v1/events/{name}";
    public static final String GET_EVENTS_BY_EVENT_TIME = "/api/v1/events/time";
    public static final String GET_EVENTS_BY_OWNER_NAME = "/api/v1/events/ownerName";
    public static final String GET_EVENTS_BY_ADDRESS = "/api/v1/events/address";
    public static final String CREATE_EVENT = "/api/v1/events";
    public static final String UPDATE_EVENT = "/api/v1/events";
    public static final String DELETE_EVENT = "/api/v1/events/{id}";

    EventService eventService;

    @GetMapping(GET_EVENT_BY_ID)
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(
                eventService.getEventById(id)
        );
    }

    @GetMapping(GET_EVENT_BY_NAME)
    public ResponseEntity<EventDto> getEventByName(@PathVariable String name) {
        return ResponseEntity.ok(
                eventService.getEventByName(name)
        );
    }

    @GetMapping(GET_EVENTS_BY_EVENT_TIME)
    public ResponseEntity<List<EventDto>> getEventsByEventTime(@RequestParam OffsetDateTime time, Pageable pageable) {
        return ResponseEntity.ok(
                eventService.getEventsByEventTime(time, pageable)
        );
    }

    @GetMapping(GET_EVENTS_BY_OWNER_NAME)
    public ResponseEntity<List<EventDto>> getEventByOwnerName(@RequestParam String name, Pageable pageable) {
        return ResponseEntity.ok(
                eventService.getEventByOwnerName(name, pageable)
        );
    }

    @GetMapping(GET_EVENTS_BY_ADDRESS)
    @Deprecated
    public ResponseEntity<List<EventDto>> getEventsByAddress(@RequestParam String address, Pageable pageable) {
        return ResponseEntity.ok(
                eventService.getEventsByAddress(address, pageable)
        );
    }

    @PostMapping(CREATE_EVENT)
    public ResponseEntity<EventDto> createEvent(EventDto creatableEvent) {
        return ResponseEntity
                .status(CREATED)
                .body(eventService.createEvent(creatableEvent));
    }

    @PatchMapping(UPDATE_EVENT)
    public ResponseEntity<EventDto> updateEvent(EventDto updatedEvent) {
        return ResponseEntity.ok(
                eventService.updateEvent(updatedEvent)
        );
    }

    @DeleteMapping(DELETE_EVENT)
    @ResponseStatus(NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
