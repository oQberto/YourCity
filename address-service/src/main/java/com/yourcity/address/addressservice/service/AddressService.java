package com.yourcity.address.addressservice.service;

import com.yourcity.address.addressservice.model.dto.address.AddressDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    AddressDto getAddressById(Long id);

    AddressDto createAddress(AddressDto dto);

    AddressDto updateAddress(AddressDto dto);


    List<AddressDto> getAddressesByCountry(String countryName, Pageable pageable);

    List<AddressDto> getAddressesByCity(String cityName, Pageable pageable);

    List<AddressDto> getAddressesByStreet(String streetName, Pageable pageable);


    void deleteAddress(Long id);
}
