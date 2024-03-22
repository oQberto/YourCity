package com.yourcity.address.addressservice.unit.repository;

import com.yourcity.address.addressservice.TestAddressServiceApplication;
import com.yourcity.address.addressservice.repository.CountryRepository;
import com.yourcity.domain.domain.model.entity.Country;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest(classes = TestAddressServiceApplication.class)
public class CountryRepositoryTest {
    CountryRepository countryRepository;

    @Test
    public void findCountryByName_whenNameExists_shouldReturnCountry() {
        var name = "USA";
        var countryId = 1L;

        Optional<Country> actualCountry = countryRepository.findCountryByName(name);

        assertThat(actualCountry).isPresent();
        assertThat(actualCountry.get().getId()).isEqualTo(countryId);
    }

    @Test
    public void findCountryByName_whenNameDoesNotExist_shouldReturnNothing() {
        var anyName = "";

        Optional<Country> actualCountry = countryRepository.findCountryByName(anyName);

        assertThat(actualCountry).isEmpty();
    }
}
