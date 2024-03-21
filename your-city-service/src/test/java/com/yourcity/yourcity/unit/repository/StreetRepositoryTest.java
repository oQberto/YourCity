package com.yourcity.yourcity.unit.repository;

import com.yourcity.yourcity.TestYourCityServiceApplication;
import com.yourcity.yourcity.model.entity.Street;
import com.yourcity.yourcity.model.entity.enums.Type;
import com.yourcity.yourcity.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@Sql({
        "classpath:sql/test-data-clear.sql",
        "classpath:sql/test-data-insert.sql"
})
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = TestYourCityServiceApplication.class)
public class StreetRepositoryTest {
    StreetRepository streetRepository;

    @Test
    public void findByName_whenNameExists_shouldReturnStreet() {
        var existedName = "Oxford Street";
        var streetId = 2L;

        Optional<Street> actualStreet = streetRepository.findByName(existedName);

        assertThat(actualStreet).isPresent();
        assertThat(actualStreet.get().getId()).isEqualTo(streetId);
    }

    @Test
    public void findByName_whenNameDoesNotExist_shouldReturnNothing() {
        var anyName = "";

        Optional<Street> actualStreet = streetRepository.findByName(anyName);

        assertThat(actualStreet).isEmpty();
    }

    @Test
    public void findAllByType_whenTypeExists_shouldReturnListOfStreets() {
        var existingType = Type.BOULEVARD;
        var pageable = PageRequest.of(0, 5);

        Page<Street> actualStreets = streetRepository.findAllByType(existingType, pageable);

        assertThat(actualStreets).hasSize(1);
    }

    @Test
    public void findAllByType_whenTypeDoesNotExist_shouldReturnEmptyList() {
        Type anyType = null;
        var anyPageable = Pageable.unpaged();

        Page<Street> actualStreets = streetRepository.findAllByType(anyType, anyPageable);

        assertThat(actualStreets).isEmpty();
    }
}
