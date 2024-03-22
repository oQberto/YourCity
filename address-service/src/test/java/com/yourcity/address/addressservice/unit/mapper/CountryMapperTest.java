package com.yourcity.address.addressservice.unit.mapper;

import com.yourcity.address.addressservice.TestAddressServiceApplication;
import com.yourcity.address.addressservice.model.dto.country.CountryDto;
import com.yourcity.address.addressservice.model.mapper.CountryMapper;
import com.yourcity.domain.domain.model.entity.Country;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = TestAddressServiceApplication.class)
public class CountryMapperTest {
    CountryMapper countryMapper;

    @Test
    public void mapToCountry_whenPassCountryDto_shouldReturnCountry() {
        var countryId = 1L;
        var countryName = "country1";
        var countryDto = CountryDto.builder()
                .id(countryId)
                .name(countryName)
                .description("country1 description")
                .build();

        Country actualCountry = countryMapper.mapToCountry(countryDto);

        assertThat(actualCountry).isNotNull();
        assertThat(actualCountry.getId()).isEqualTo(countryId);
        assertThat(actualCountry.getName()).isEqualTo(countryName);
    }

    @Test
    public void mapToCountryDto_whenPassCountry_shouldReturnCountryDto() {
        var countryId = 1L;
        var countryName = "country1";
        var country = Country.builder()
                .id(countryId)
                .name(countryName)
                .description("country1 description")
                .build();

        CountryDto actualCountry = countryMapper.mapToCountryDto(country);

        assertThat(actualCountry).isNotNull();
        assertThat(actualCountry.getId()).isEqualTo(countryId);
        assertThat(actualCountry.getName()).isEqualTo(countryName);
    }

    @Test
    public void updateCountry_whenPassCountryDtoAndCountry_shouldReturnCountry() {
        var countryId = 1L;
        var newCountryName = "newCountryName";
        var newCountryDescription = "new country description";
        var country = Country.builder()
                .id(countryId)
                .name("country1")
                .description("country1 description")
                .build();
        var countryDto = CountryDto.builder()
                .id(countryId)
                .name(newCountryName)
                .description(newCountryDescription)
                .build();

        Country updatedCountry = countryMapper.updateCountry(countryDto, country);

        assertThat(updatedCountry).isNotNull();
        assertThat(updatedCountry.getName()).isEqualTo(newCountryName);
        assertThat(updatedCountry.getDescription()).isEqualTo(newCountryDescription);
    }
}
