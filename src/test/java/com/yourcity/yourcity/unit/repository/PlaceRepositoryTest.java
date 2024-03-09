package com.yourcity.yourcity.unit.repository;

import com.yourcity.yourcity.TestYourCityApplication;
import com.yourcity.yourcity.model.entity.Place;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
import com.yourcity.yourcity.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Sql({
        "classpath:sql/test-data-clear.sql",
        "classpath:sql/test-data-insert.sql"
})
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = TestYourCityApplication.class)
public class PlaceRepositoryTest {
    PlaceRepository placeRepository;

    @Test
    public void findByName_whenNameExists_ShouldReturnPlace() {
        var existedName = "Place1";
        var placeId = 1L;

        Optional<Place> actualPlace = placeRepository.findByName(existedName);

        assertThat(actualPlace).isPresent();
        assertThat(actualPlace.get().getId()).isEqualTo(placeId);

    }

    @Test
    public void findByName_whenNameDoesNotExist_ShouldReturnNothing() {
        var anyName = anyString();

        Optional<Place> actualPlace = placeRepository.findByName(anyName);

        assertThat(actualPlace).isEmpty();
    }

    @Test
    public void findAllByCategory_whenPlaceCategoryExists_shouldReturnListOfPlaces() {
        var existedCategory = PlaceCategory.CINEMA;
        var pageable = PageRequest.of(0, 5);

        Page<Place> actualPlaces = placeRepository.findAllByCategory(existedCategory, pageable);
        List<Long> placeIds = actualPlaces.stream()
                .map(Place::getId)
                .toList();

        assertThat(actualPlaces).hasSize(5);
        assertThat(placeIds).contains(3L, 6L, 9L, 12L, 15L);
    }

    @Test
    public void findAllByCategory_whenPlaceCategoryDoesNotExist_shouldReturnEmptyList() {
        var anyCategory = any(PlaceCategory.class);
        var anyPageable = any(Pageable.class);

        Page<Place> actualPlaces = placeRepository.findAllByCategory(anyCategory, anyPageable);

        assertThat(actualPlaces).isEmpty();
    }
}
