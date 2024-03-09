package com.yourcity.yourcity.controller.api;

import com.yourcity.yourcity.dto.place.PlaceDto;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
import com.yourcity.yourcity.service.PlaceService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PlaceController {
    public static final String GET_PLACE_BY_ID = "/api/v1/places/{id}";
    public static final String GET_PLACE_BY_NAME = "/api/v1/places/{name}";
    public static final String GET_PLACES_BY_CATEGORY = "/api/v1/places";
    public static final String CREATE_PLACE = "/api/v1/places";
    public static final String UPDATE_PLACE = "/api/v1/places";
    public static final String DELETE_PLACE = "/api/v1/places/{id}";

    PlaceService placeService;

    @GetMapping(GET_PLACE_BY_ID)
    public ResponseEntity<PlaceDto> getPlaceById(@PathVariable Long id) {
        return ResponseEntity.ok(
                placeService.getPlaceById(id)
        );
    }

    @GetMapping(GET_PLACE_BY_NAME)
    public ResponseEntity<PlaceDto> getPlaceByName(@PathVariable String name) {
        return ResponseEntity.ok(
                placeService.getPlaceByName(name)
        );
    }

    @GetMapping(GET_PLACES_BY_CATEGORY)
    public ResponseEntity<List<PlaceDto>> getPlacesByCategory(@RequestParam PlaceCategory category, Pageable pageable) {
        return ResponseEntity.ok(
                placeService.getPlacesByCategory(category, pageable)
        );
    }

    @PostMapping(CREATE_PLACE)
    public ResponseEntity<PlaceDto> createPlace(PlaceDto creatablePlace) {
        return ResponseEntity
                .status(CREATED)
                .body(placeService.createPlace(creatablePlace));
    }

    @PatchMapping(UPDATE_PLACE)
    public ResponseEntity<PlaceDto> updatePlace(PlaceDto updatedPlace) {
        return ResponseEntity.ok(
                placeService.updatePlace(updatedPlace)
        );
    }

    @DeleteMapping(DELETE_PLACE)
    @ResponseStatus(NO_CONTENT)
    public void deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
    }
}
