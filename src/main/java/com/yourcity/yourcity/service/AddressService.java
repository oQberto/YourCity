package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.address.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    AddressDto getAddressById(Long id);

    AddressDto getAddressByRoomNumber(Short roomNumber);

    AddressDto getAddressByBuildingNumber(Short buildingNumber);

    AddressDto createAddress(AddressDto dto);

    AddressDto updateAddress(AddressDto dto);


    List<AddressDto> getAddressesByCountry(String countryName);

    List<AddressDto> getAddressesByCity(String cityName);

    List<AddressDto> getAddressesByStreet(String streetName);


    void deleteAddress(Long id);
}
