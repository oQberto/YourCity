package com.yourcity.yourcity.repository;

import com.yourcity.yourcity.model.entity.Place;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
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
