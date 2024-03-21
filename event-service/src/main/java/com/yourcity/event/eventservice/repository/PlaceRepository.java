package com.yourcity.event.eventservice.repository;

import com.yourcity.domain.entity.Place;
import com.yourcity.domain.enums.PlaceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByName(String name);

    Page<Place> findAllByCategory(PlaceCategory place, Pageable pageable);
}
