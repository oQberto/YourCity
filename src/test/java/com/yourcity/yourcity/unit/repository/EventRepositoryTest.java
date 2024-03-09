package com.yourcity.yourcity.unit.repository;

import com.yourcity.yourcity.TestYourCityApplication;
import com.yourcity.yourcity.model.entity.Event;
import com.yourcity.yourcity.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@RequiredArgsConstructor
@Sql({
        "classpath:sql/test-data-clear.sql",
        "classpath:sql/test-data-insert.sql"
})
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = TestYourCityApplication.class)
public class EventRepositoryTest {
    EventRepository eventRepository;

    @Test
    public void findByName_whenNameExists_shouldReturnEvent() {
        var name = "Event1";

        Optional<Event> actualUser = eventRepository.findByName(name);

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getId()).isEqualTo(1L);
    }

    @Test
    public void findByName_whenNameDoesNotExists_shouldReturnEmptyOptional() {
        var notExistedName = anyString();

        Optional<Event> actualUser = eventRepository.findByName(notExistedName);

        assertThat(actualUser).isEmpty();
    }

    @Test
    public void findAllByEventTime_whenEventTimeExists_shouldReturnListOfEvents() {
        var existingEventTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusDays(1L);
        var pageableWithFiveElements = PageRequest.of(0, 5);
        var pageableWithHundredElements = PageRequest.of(0, 100);

        Page<Event> actualFiveEvents = eventRepository.findAllByEventTime(existingEventTime, pageableWithFiveElements);
        Page<Event> actualHundredEvents = eventRepository.findAllByEventTime(existingEventTime, pageableWithHundredElements);

        assertThat(actualFiveEvents).hasSize(5);
        assertThat(actualHundredEvents).hasSize(30);
    }

    @Test
    public void findAllByEventTime_whenEventTimeDoesNotExist_shouldReturnEmptyList() {
        var notExistedEventTime = any(LocalDateTime.class);
        var anyPageable = any(Pageable.class);

        Page<Event> actualEvents = eventRepository.findAllByEventTime(notExistedEventTime, anyPageable);

        assertThat(actualEvents).isEmpty();
    }

    @Test
    public void findAllByOwnerUsername_whenOwnerExists_shouldReturnListOfEvents() {
        var ownerName = "user1";
        var pageableWithFiveElements = PageRequest.of(0, 5);
        var pageableWithHundredElements = PageRequest.of(0, 100);

        Page<Event> actualFiveEvents = eventRepository.findAllByOwnerUsername(ownerName, pageableWithFiveElements);
        Page<Event> actualHundredEvents = eventRepository.findAllByOwnerUsername(ownerName, pageableWithHundredElements);

        assertThat(actualFiveEvents).hasSize(5);
        assertThat(actualHundredEvents).hasSize(30);
    }

    @Test
    public void findAllByOwnerUsername_whenOwnerDoesNotExist_shouldReturnEmptyList() {
        var notExistedName = anyString();
        var anyPageable = any(Pageable.class);

        Page<Event> actualEvents = eventRepository.findAllByOwnerUsername(notExistedName, anyPageable);

        assertThat(actualEvents).isEmpty();
    }
}
