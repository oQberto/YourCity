package com.yourcity.address.addressservice.service.impl;

import com.yourcity.address.addressservice.model.dto.street.StreetDto;
import com.yourcity.address.addressservice.model.mapper.StreetMapper;
import com.yourcity.address.addressservice.repository.StreetRepository;
import com.yourcity.address.addressservice.service.StreetService;
import com.yourcity.address.addressservice.service.exception.EntityCreationException;
import com.yourcity.address.addressservice.service.exception.EntityUpdateException;
import com.yourcity.domain.domain.model.enums.Type;
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
public class StreetServiceImpl implements StreetService {
    public static final String GET_STREET_BY_ID = "Couldn't find a street with id \"%d\".";
    public static final String GET_STREET_BY_NAME = "Couldn't find a street with name \"%s\".";
    public static final String CREATE_STREET = "Couldn't create a street.";
    public static final String UPDATE_STREET = "Couldn't update a street.";

    StreetRepository streetRepository;
    StreetMapper streetMapper;

    @Override
    public StreetDto getStreetById(Long id) {
        return streetRepository
                .findById(id)
                .map(streetMapper::mapToStreetDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_STREET_BY_ID, id)
                        ));
    }

    @Override
    public StreetDto getStreetByName(String name) {
        return streetRepository
                .findByName(name)
                .map(streetMapper::mapToStreetDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_STREET_BY_NAME, name)
                        ));
    }

    @Override
    @Transactional
    public StreetDto createStreet(StreetDto dto) {
        return Optional.of(dto)
                .map(streetMapper::mapToStreet)
                .map(streetRepository::saveAndFlush)
                .map(streetMapper::mapToStreetDto)
                .orElseThrow(() -> new EntityCreationException(CREATE_STREET));
    }

    @Override
    @Transactional
    public StreetDto updateStreet(StreetDto dto) {
        return streetRepository
                .findById(dto.getId())
                .map(street -> streetMapper.updateStreet(dto, street))
                .map(streetRepository::saveAndFlush)
                .map(streetMapper::mapToStreetDto)
                .orElseThrow(() -> new EntityUpdateException(UPDATE_STREET));
    }

    @Override
    public List<StreetDto> getStreetsByType(Type type, Pageable pageable) {
        return streetRepository
                .findAllByType(type, pageable)
                .stream()
                .map(streetMapper::mapToStreetDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteStreet(Long id) {
        streetRepository
                .findById(id)
                .ifPresent(streetRepository::delete);
    }
}