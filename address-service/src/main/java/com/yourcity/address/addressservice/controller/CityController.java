package com.yourcity.address.addressservice.controller;

import com.yourcity.address.addressservice.model.dto.city.CityDto;
import com.yourcity.address.addressservice.service.CityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CityController {
    public static final String GET_CITY_BY_ID = "/api/v1/cities/{id}";
    public static final String CREATE_CITY = "/api/v1/cities";
    public static final String UPDATE_CITY = "/api/v1/cities";
    public static final String DELETE_CITY = "/api/v1/cities/{id}";

    CityService cityService;

    @GetMapping(GET_CITY_BY_ID)
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(
                cityService.getCityById(id)
        );
    }

    @PostMapping(CREATE_CITY)
    public ResponseEntity<CityDto> createCity(CityDto createdCity) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityService.createCity(createdCity));
    }

    @PatchMapping(UPDATE_CITY)
    public ResponseEntity<CityDto> updateCity(CityDto updatedCity) {
        return ResponseEntity.ok(
                cityService.updateCity(updatedCity)
        );
    }

    @DeleteMapping(DELETE_CITY)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
