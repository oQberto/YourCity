package com.yourcity.address.addressservice.service.impl;

import com.yourcity.address.addressservice.model.dto.country.CountryDto;
import com.yourcity.address.addressservice.model.mapper.CountryMapper;
import com.yourcity.address.addressservice.repository.CountryRepository;
import com.yourcity.address.addressservice.service.CountryService;
import com.yourcity.address.addressservice.service.exception.EntityCreationException;
import com.yourcity.address.addressservice.service.exception.EntityUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CountryServiceImpl implements CountryService {
    public static final String COUNTRY_NOT_FOUND_BY_ID = "Couldn't find a country with id \"%d\".";
    public static final String COUNTRY_NOT_FOUND_BY_NAME = "Couldn't find a country with name \"%s\".";
    public static final String COUNTRY_CREATION = "Couldn't create a country.";
    public static final String COUNTRY_UPDATE = "Couldn't update a country.";

    CountryRepository countryRepository;
    CountryMapper countryMapper;

    @Override
    public CountryDto getCountryById(Long id) {
        return countryRepository
                .findById(id)
                .map(countryMapper::mapToCountryDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(COUNTRY_NOT_FOUND_BY_ID, id)
                        ));
    }

    @Override
    public CountryDto getCountryByName(String name) {
        return countryRepository
                .findCountryByName(name)
                .map(countryMapper::mapToCountryDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(COUNTRY_NOT_FOUND_BY_NAME, name)
                        ));
    }

    @Override
    @Transactional
    public CountryDto createCountry(CountryDto dto) {
        return Optional.of(dto)
                .map(countryMapper::mapToCountry)
                .map(countryRepository::saveAndFlush)
                .map(countryMapper::mapToCountryDto)
                .orElseThrow(() -> new EntityCreationException(COUNTRY_CREATION));
    }

    @Override
    @Transactional
    public CountryDto updateCountry(CountryDto dto) {
        return countryRepository
                .findById(dto.getId())
                .map(country -> countryMapper.updateCountry(dto, country))
                .map(countryRepository::saveAndFlush)
                .map(countryMapper::mapToCountryDto)
                .orElseThrow(() -> new EntityUpdateException(COUNTRY_UPDATE));
    }

    @Override
    @Transactional
    public void deleteCountry(Long id) {
        countryRepository.findById(id)
                .ifPresent(countryRepository::delete);
    }
}
