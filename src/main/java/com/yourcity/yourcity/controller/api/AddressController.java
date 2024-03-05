package com.yourcity.yourcity.controller.api;

import com.yourcity.yourcity.dto.address.AddressDto;
import com.yourcity.yourcity.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddressController {
    public static final String GET_ADDRESS_BY_ID = "/api/v1/addresses/{id}";
    public static final String GET_ADDRESSES_BY_ROOM_NUMBER = "/api/v1/addresses/room/{number}";
    public static final String GET_ADDRESSES_BY_BUILDING_NUMBER = "/api/v1/addresses/building/{number}";
    public static final String GET_ADDRESSES_BY_COUNTRY_NAME = "/api/v1/addresses/country/{name}";
    public static final String GET_ADDRESSES_BY_STREET_NAME = "/api/v1/addresses/street/{name}";
    public static final String GET_ADDRESSES_BY_CITY_NAME = "/api/v1/addresses/city/{name}";
    public static final String CREATE_ADDRESS = "/api/v1/addresses";
    public static final String UPDATE_ADDRESS = "/api/v1/addresses";
    public static final String DELETE_ADDRESS = "/api/v1/addresses/{id}";

    AddressService addressService;

    @GetMapping(GET_ADDRESS_BY_ID)
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(
                addressService.getAddressById(id)
        );
    }

    @GetMapping(GET_ADDRESSES_BY_ROOM_NUMBER)
    public ResponseEntity<AddressDto> getAddressByRoomNumber(@PathVariable Short number) {
        return ResponseEntity.ok(
                addressService.getAddressByRoomNumber(number)
        );
    }

    @GetMapping(GET_ADDRESSES_BY_BUILDING_NUMBER)
    public ResponseEntity<AddressDto> getAddressByBuildingNumber(@PathVariable Short number) {
        return ResponseEntity.ok(
                addressService.getAddressByBuildingNumber(number)
        );
    }

    @GetMapping(GET_ADDRESSES_BY_COUNTRY_NAME)
    public ResponseEntity<List<AddressDto>> getAddressesByCountryName(@PathVariable String name) {
        return ResponseEntity.ok(
                addressService.getAddressesByCountry(name)
        );
    }

    @GetMapping(GET_ADDRESSES_BY_CITY_NAME)
    public ResponseEntity<List<AddressDto>> getAddressesByCityName(@PathVariable String name) {
        return ResponseEntity.ok(
                addressService.getAddressesByCity(name)
        );
    }

    @GetMapping(GET_ADDRESSES_BY_STREET_NAME)
    public ResponseEntity<List<AddressDto>> getAddressesByStreetName(@PathVariable String name) {
        return ResponseEntity.ok(
                addressService.getAddressesByStreet(name)
        ) ;
    }

    @PostMapping(CREATE_ADDRESS)
    public ResponseEntity<AddressDto> createAddress(AddressDto creatableAddress) {
        return ResponseEntity
                .status(CREATED)
                .body(addressService.createAddress(creatableAddress));
    }

    @PatchMapping(UPDATE_ADDRESS)
    public ResponseEntity<AddressDto> updateAddress(AddressDto updatedAddress) {
        return ResponseEntity.ok(
                addressService.updateAddress(updatedAddress)
        );
    }

    @DeleteMapping(DELETE_ADDRESS)
    @ResponseStatus(NO_CONTENT)
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}
