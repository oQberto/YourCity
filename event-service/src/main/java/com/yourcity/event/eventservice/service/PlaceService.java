package com.yourcity.event.eventservice.service;

import com.yourcity.domain.domain.model.enums.PlaceCategory;
import com.yourcity.event.eventservice.model.dto.place.PlaceDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {

    PlaceDto getPlaceById(Long id);

    PlaceDto getPlaceByName(String name);

    List<PlaceDto> getPlacesByCategory(PlaceCategory category, Pageable pageable);

    PlaceDto createPlace(PlaceDto dto);

    PlaceDto updatePlace(PlaceDto dto);

    void deletePlace(Long id);
}
