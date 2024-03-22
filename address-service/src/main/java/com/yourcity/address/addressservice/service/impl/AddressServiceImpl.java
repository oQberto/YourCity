package com.yourcity.address.addressservice.service.impl;

import com.yourcity.address.addressservice.model.dto.address.AddressDto;
import com.yourcity.address.addressservice.model.mapper.AddressMapper;
import com.yourcity.address.addressservice.repository.AddressRepository;
import com.yourcity.address.addressservice.service.AddressService;
import com.yourcity.address.addressservice.service.exception.EntityCreationException;
import com.yourcity.address.addressservice.service.exception.EntityUpdateException;
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
public class AddressServiceImpl implements AddressService {
    public static final String GET_ADDRESS_BY_ID = "Couldn't find an address with id \"%d\".";
    public static final String CREATE_ADDRESS = "Couldn't create an address.";
    public static final String UPDATE_ADDRESS = "Couldn't update an address.";

    AddressRepository addressRepository;
    AddressMapper addressMapper;

    @Override
    public AddressDto getAddressById(Long id) {
        return addressRepository
                .findById(id)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_ADDRESS_BY_ID, id)
                        ));
    }

    @Override
    @Transactional
    public AddressDto createAddress(AddressDto dto) {
        return Optional.of(dto)
                .map(addressMapper::mapToAddress)
                .map(addressRepository::saveAndFlush)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() -> new EntityCreationException(CREATE_ADDRESS));
    }

    @Override
    @Transactional
    public AddressDto updateAddress(AddressDto dto) {
        return addressRepository
                .findById(dto.getId())
                .map(address -> addressMapper.updateAddress(dto, address))
                .map(addressRepository::saveAndFlush)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() -> new EntityUpdateException(UPDATE_ADDRESS));
    }

    @Override
    public List<AddressDto> getAddressesByCountry(String countryName, Pageable pageable) {
        return addressRepository
                .findAllByCountryName(countryName, pageable)
                .stream()
                .map(addressMapper::mapToAddressDto)
                .toList();
    }

    @Override
    public List<AddressDto> getAddressesByCity(String cityName, Pageable pageable) {
        return addressRepository
                .findAllByCityName(cityName, pageable)
                .stream()
                .map(addressMapper::mapToAddressDto)
                .toList();
    }

    @Override
    public List<AddressDto> getAddressesByStreet(String streetName, Pageable pageable) {
        return addressRepository
                .findAllByStreetName(streetName, pageable)
                .stream()
                .map(addressMapper::mapToAddressDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        addressRepository
                .findById(id)
                .ifPresent(addressRepository::delete);
    }
}
