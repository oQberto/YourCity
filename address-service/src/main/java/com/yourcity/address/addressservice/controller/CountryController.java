package com.yourcity.address.addressservice.controller;

import com.yourcity.address.addressservice.model.dto.country.CountryDto;
import com.yourcity.address.addressservice.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CountryController {
    public static final String GET_COUNTRY_BY_ID = "/api/v1/countries/{id}";
    public static final String GET_COUNTRY_BY_NAME = "/api/v1/countries/{countryName}";
    public static final String CREATE_COUNTRY = "/api/v1/countries";
    public static final String UPDATE_COUNTRY = "/api/v1/countries";
    public static final String DELETE_COUNTRY = "/api/v1/countries/{id}";

    CountryService countryService;

    @GetMapping(GET_COUNTRY_BY_ID)
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        return ResponseEntity.ok(
                countryService.getCountryById(id)
        );
    }

    @GetMapping(GET_COUNTRY_BY_NAME)
    public ResponseEntity<CountryDto> getCountryByName(@PathVariable("countryName") String name) {
        return ResponseEntity.ok(
                countryService.getCountryByName(name)
        );
    }

    @PostMapping(CREATE_COUNTRY)
    public ResponseEntity<CountryDto> createCountry(CountryDto creatableCountry) {
        return ResponseEntity
                .status(CREATED)
                .body(countryService.createCountry(creatableCountry));
    }

    @PatchMapping(UPDATE_COUNTRY)
    public ResponseEntity<CountryDto> updateCountry(CountryDto updatableCountry) {
        return ResponseEntity.ok(
                countryService.updateCountry(updatableCountry)
        );
    }

    @DeleteMapping(DELETE_COUNTRY)
    @ResponseStatus(NO_CONTENT)
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }
}
