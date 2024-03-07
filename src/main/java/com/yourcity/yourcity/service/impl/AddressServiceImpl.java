package com.yourcity.yourcity.service.impl;

import com.yourcity.yourcity.dto.address.AddressDto;
import com.yourcity.yourcity.dto.mapper.AddressMapper;
import com.yourcity.yourcity.repository.AddressRepository;
import com.yourcity.yourcity.service.AddressService;
import com.yourcity.yourcity.service.exception.address.AddressCreationException;
import com.yourcity.yourcity.service.exception.address.AddressUpdateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public static final String GET_ADDRESS_BY_ROOM_NUMBER = "Couldn't find an address with room number \"%d\".";
    public static final String GET_ADDRESS_BY_BUILDING_NUMBER = "Couldn't find an address with building number \"%d\".";
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
    public AddressDto getAddressByRoomNumber(Short roomNumber) {
        return addressRepository
                .findByRoomNumber(roomNumber)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_ADDRESS_BY_ROOM_NUMBER, roomNumber)
                        ));
    }

    @Override
    public AddressDto getAddressByBuildingNumber(Short buildingNumber) {
        return addressRepository
                .findByBuildingNumber(buildingNumber)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(GET_ADDRESS_BY_BUILDING_NUMBER, buildingNumber)
                        ));
    }

    @Override
    @Transactional
    public AddressDto createAddress(AddressDto dto) {
        return Optional.of(dto)
                .map(addressMapper::mapToAddress)
                .map(addressRepository::saveAndFlush)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() -> new AddressCreationException(CREATE_ADDRESS));
    }

    @Override
    @Transactional
    public AddressDto updateAddress(AddressDto dto) {
        return addressRepository
                .findById(dto.getId())
                .map(address -> addressMapper.updateAddress(dto, address))
                .map(addressRepository::saveAndFlush)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() -> new AddressUpdateException(UPDATE_ADDRESS));
    }

    @Override
    public List<AddressDto> getAddressesByCountry(String countryName) {
        return addressRepository
                .findAllByCountryName(countryName)
                .stream()
                .map(addressMapper::mapToAddressDto)
                .toList();
    }

    @Override
    public List<AddressDto> getAddressesByCity(String cityName) {
        return addressRepository
                .findAllByCityName(cityName)
                .stream()
                .map(addressMapper::mapToAddressDto)
                .toList();
    }

    @Override
    public List<AddressDto> getAddressesByStreet(String streetName) {
        return addressRepository
                .findAllByStreetName(streetName)
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
