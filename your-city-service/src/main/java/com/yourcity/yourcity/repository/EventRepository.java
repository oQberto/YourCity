package com.yourcity.yourcity.repository;

import com.yourcity.yourcity.model.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByName(String name);

    @Query("""
            SELECT e
            FROM Event e
            WHERE date_trunc('minute', e.eventTime) = :time
            """)
    Page<Event> findAllByEventTime(LocalDateTime time, Pageable pageable);

    Page<Event> findAllByOwnerUsername(String name, Pageable pageable);
}
