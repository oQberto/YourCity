package com.yourcity.yourcity.service.impl;

import com.yourcity.yourcity.dto.mapper.PlaceMapper;
import com.yourcity.yourcity.dto.place.PlaceDto;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
import com.yourcity.yourcity.repository.PlaceRepository;
import com.yourcity.yourcity.service.PlaceService;
import com.yourcity.yourcity.service.exception.EntityCreationException;
import com.yourcity.yourcity.service.exception.EntityUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PlaceServiceImpl implements PlaceService {
    public static final String GET_PLACE_BY_ID = "Couldn't find a place with id\"%d\"";
    public static final String GET_PLACE_BY_NAME = "Couldn't fund a place with name \"%s\"";
    public static final String CREATE_PLACE = "Couldn't create a place.";
    public static final String UPDATE_PLACE = "Couldn't update a place.";

    PlaceRepository placeRepository;
    PlaceMapper placeMapper;

    @Override
    public PlaceDto getPlaceById(Long id) {
        return placeRepository
                .findById(id)
                .map(placeMapper::mapToPlaceDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_PLACE_BY_ID, id)
                        ));
    }

    @Override
    public PlaceDto getPlaceByName(String name) {
        return placeRepository
                .findByName(name)
                .map(placeMapper::mapToPlaceDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_PLACE_BY_NAME, name)
                        ));
    }

    @Override
    public List<PlaceDto> getPlacesByCategory(PlaceCategory category, Pageable pageable) {
        return placeRepository
                .findAllByCategory(category, pageable)
                .stream()
                .map(placeMapper::mapToPlaceDto)
                .toList();
    }

    @Override
    @Transactional
    public PlaceDto createPlace(PlaceDto dto) {
        return Optional.of(dto)
                .map(placeMapper::mapToPlace)
                .map(placeRepository::saveAndFlush)
                .map(placeMapper::mapToPlaceDto)
                .orElseThrow(() -> new EntityCreationException(CREATE_PLACE));
    }

    @Override
    @Transactional
    public PlaceDto updatePlace(PlaceDto dto) {
        return placeRepository
                .findById(dto.getId())
                .map(place -> placeMapper.updatePlace(dto, place))
                .map(placeRepository::saveAndFlush)
                .map(placeMapper::mapToPlaceDto)
                .orElseThrow(() -> new EntityUpdateException(UPDATE_PLACE));
    }

    @Override
    @Transactional
    public void deletePlace(Long id) {
        placeRepository
                .findById(id)
                .ifPresent(placeRepository::delete);
    }
}
