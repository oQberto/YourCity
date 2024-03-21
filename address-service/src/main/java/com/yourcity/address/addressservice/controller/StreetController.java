package com.yourcity.address.addressservice.controller;

import com.yourcity.address.addressservice.model.dto.street.StreetDto;
import com.yourcity.address.addressservice.service.StreetService;
import com.yourcity.domain.enums.Type;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class StreetController {
    public static final String GET_STREET_BY_ID = "/api/v1/streets/{id}";
    public static final String GET_STREET_BY_NAME = "/api/v1/streets/{name}";
    public static final String GET_STREETS_BY_TYPE = "/api/v1/streets";
    public static final String CREATE_STREET = "/api/v1/streets";
    public static final String UPDATE_STREET = "/api/v1/streets";
    public static final String DELETE_STREET = "/api/v1/streets";

    StreetService streetService;

    @GetMapping(GET_STREET_BY_ID)
    public ResponseEntity<StreetDto> getStreetById(@PathVariable Long id) {
        return ResponseEntity.ok(
                streetService.getStreetById(id)
        );
    }

    @GetMapping(GET_STREET_BY_NAME)
    public ResponseEntity<StreetDto> getStreetByName(@PathVariable String name) {
        return ResponseEntity.ok(
                streetService.getStreetByName(name)
        );
    }

    @GetMapping(GET_STREETS_BY_TYPE)
    public ResponseEntity<List<StreetDto>> getStreetsByType(@RequestParam Type type, Pageable pageable) {
        return ResponseEntity.ok(
                streetService.getStreetsByType(type, pageable)
        );
    }

    @PostMapping(CREATE_STREET)
    public ResponseEntity<StreetDto> createStreet(StreetDto creatableStreet) {
        return ResponseEntity
                .status(CREATED)
                .body(streetService.createStreet(creatableStreet));
    }

    @PatchMapping(UPDATE_STREET)
    public ResponseEntity<StreetDto> updateStreet(StreetDto updatedStreet) {
        return ResponseEntity.ok(
                streetService.updateStreet(updatedStreet)
        );
    }

    @DeleteMapping(DELETE_STREET)
    @ResponseStatus(NO_CONTENT)
    public void deleteStreet(Long id) {
        streetService.deleteStreet(id);
    }
}
