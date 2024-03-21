package com.yourcity.yourcity.unit.mapper;

import com.yourcity.yourcity.YourCityServiceApplication;
import com.yourcity.yourcity.dto.mapper.PlaceMapper;
import com.yourcity.yourcity.dto.place.PlaceDto;
import com.yourcity.yourcity.model.entity.Place;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = YourCityServiceApplication.class)
public class PlaceMapperTest {
    PlaceMapper placeMapper;

    @Test
    public void mapToPlace_whenPassPlaceDto_shouldReturnPlace() {
        var placeId = 1L;
        var placeName = "place1";
        var placeDto = PlaceDto.builder()
                .id(placeId)
                .name(placeName)
                .description("place1 description")
                .build();

        Place actualPlace = placeMapper.mapToPlace(placeDto);

        assertThat(actualPlace).isNotNull();
        assertThat(actualPlace.getId()).isEqualTo(placeId);
        assertThat(actualPlace.getName()).isEqualTo(placeName);
    }

    @Test
    public void mapToPlaceDto_whenPassPlace_shouldReturnPlaceDto() {
        var placeId = 1L;
        var placeName = "place1";
        var place = Place.builder()
                .id(placeId)
                .name(placeName)
                .description("place1 description")
                .build();

        PlaceDto actualPlace = placeMapper.mapToPlaceDto(place);

        assertThat(actualPlace).isNotNull();
        assertThat(actualPlace.getId()).isEqualTo(placeId);
        assertThat(actualPlace.getName()).isEqualTo(placeName);
    }

    @Test
    public void updatePlace_whenPassPlaceDtoAndCountry_shouldReturnPlace() {
        var placeId = 1L;
        var newPlaceName = "newPlaceName";
        var newPlaceDescription = "new place description";
        var place = Place.builder()
                .id(placeId)
                .name("place1")
                .description("place1 description")
                .build();
        var placeDto = PlaceDto.builder()
                .id(placeId)
                .name(newPlaceName)
                .description(newPlaceDescription)
                .build();

        Place updatedPlace = placeMapper.updatePlace(placeDto, place);

        assertThat(updatedPlace).isNotNull();
        assertThat(updatedPlace.getName()).isEqualTo(newPlaceName);
        assertThat(updatedPlace.getDescription()).isEqualTo(newPlaceDescription);
    }
}
