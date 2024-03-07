package com.yourcity.yourcity.repository;

import com.yourcity.yourcity.model.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByName(String name);

    Page<Event> findAllByEventTime(OffsetDateTime time, Pageable pageable);

    Page<Event> findAllByOwnerUsername(String name, Pageable pageable);
}
