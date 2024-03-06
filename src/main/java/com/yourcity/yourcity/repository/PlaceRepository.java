package com.yourcity.yourcity.repository;

import com.yourcity.yourcity.model.entity.Place;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByName(String name);

    List<Place> findAllByCategory(PlaceCategory place);
}
