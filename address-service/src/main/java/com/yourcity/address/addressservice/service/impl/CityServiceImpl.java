package com.yourcity.address.addressservice.service.impl;

import com.yourcity.address.addressservice.model.dto.city.CityDto;
import com.yourcity.address.addressservice.model.mapper.CityMapper;
import com.yourcity.address.addressservice.repository.CityRepository;
import com.yourcity.address.addressservice.service.CityService;
import com.yourcity.address.addressservice.service.exception.EntityCreationException;
import com.yourcity.address.addressservice.service.exception.EntityUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CityServiceImpl implements CityService {
    public static final String CITY_CREATION = "Couldn't create a city.";
    public static final String CITY_NOT_FOUND = "Couldn't find a city with id \"%d\".";
    public static final String CITY_UPDATE = "Couldn't update a city.";

    CityRepository cityRepository;
    CityMapper cityMapper;

    @Override
    @Transactional
    public CityDto createCity(CityDto dto) {
        return Optional.of(dto)
                .map(cityMapper::mapToCity)
                .map(cityRepository::saveAndFlush)
                .map(cityMapper::mapToCityDto)
                .orElseThrow(() -> new EntityCreationException(CITY_CREATION));
    }

    @Override
    public CityDto getCityById(Long id) {
        return cityRepository
                .findById(id)
                .map(cityMapper::mapToCityDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(CITY_NOT_FOUND, id)
                        ));
    }

    @Override
    @Transactional
    public CityDto updateCity(CityDto dto) {
        return cityRepository
                .findById(dto.getId())
                .map(user -> cityMapper.updateCity(dto, user))
                .map(cityRepository::saveAndFlush)
                .map(cityMapper::mapToCityDto)
                .orElseThrow(() -> new EntityUpdateException(CITY_UPDATE));
    }

    @Override
    @Transactional
    public void deleteCity(Long id) {
        cityRepository.findById(id)
                .ifPresent(cityRepository::delete);
    }
}
