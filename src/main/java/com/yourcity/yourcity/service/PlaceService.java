package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.place.PlaceDto;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
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
