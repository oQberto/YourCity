package com.yourcity.yourcity.unit.mapper;

import com.yourcity.yourcity.YourCityServiceApplication;
import com.yourcity.yourcity.dto.event.EventDto;
import com.yourcity.yourcity.dto.mapper.EventMapper;
import com.yourcity.yourcity.model.entity.Event;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = YourCityServiceApplication.class)
public class EventMapperTest {
    EventMapper eventMapper;

    @Test
    public void mapToEvent_whenPassEventDto_shouldReturnEvent() {
        var eventId = 1L;
        var eventName = "event1";
        var eventDto = EventDto.builder()
                .id(eventId)
                .name(eventName)
                .description("event1 description")
                .build();

        Event actualEvent = eventMapper.mapToEvent(eventDto);

        assertThat(actualEvent).isNotNull();
        assertThat(actualEvent.getId()).isEqualTo(eventId);
        assertThat(actualEvent.getName()).isEqualTo(eventName);
    }

    @Test
    public void mapToEventDto_whenPassEvent_shouldReturnEventDto() {
        var eventId = 1L;
        var eventName = "event1";
        var event = Event.builder()
                .id(eventId)
                .name(eventName)
                .description("event1 description")
                .build();

        EventDto actualEvent = eventMapper.mapToEventDto(event);

        assertThat(actualEvent).isNotNull();
        assertThat(actualEvent.getId()).isEqualTo(eventId);
        assertThat(actualEvent.getName()).isEqualTo(eventName);
    }

    @Test
    public void updateEvent_whenPassEventDtoAndCountry_shouldReturnEvent() {
        var eventId = 1L;
        var newEventName = "newEventName";
        var newEventDescription = "new event description";
        var event = Event.builder()
                .id(eventId)
                .name("event1")
                .description("event1 description")
                .build();
        var eventDto = EventDto.builder()
                .id(eventId)
                .name(newEventName)
                .description(newEventDescription)
                .build();

        Event updatedCountry = eventMapper.updateEvent(eventDto, event);

        assertThat(updatedCountry).isNotNull();
        assertThat(updatedCountry.getName()).isEqualTo(newEventName);
        assertThat(updatedCountry.getDescription()).isEqualTo(newEventDescription);
    }
}
