package com.yourcity.yourcity.unit.mapper;

import com.yourcity.yourcity.YourCityServiceApplication;
import com.yourcity.yourcity.dto.city.CityDto;
import com.yourcity.yourcity.dto.mapper.CityMapper;
import com.yourcity.yourcity.model.entity.City;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = YourCityServiceApplication.class)
public class CityMapperTest {
    CityMapper cityMapper;

    @Test
    public void mapToCity_whenPassCityDto_shouldReturnCity() {
        var cityId = 1L;
        var cityName = "city1";
        var cityDto = CityDto.builder()
                .id(cityId)
                .name(cityName)
                .description("city1 description")
                .build();

        City actualCity = cityMapper.mapToCity(cityDto);

        assertThat(actualCity).isNotNull();
        assertThat(actualCity.getId()).isEqualTo(cityId);
        assertThat(actualCity.getName()).isEqualTo(cityName);
    }

    @Test
    public void mapToCityDto_whenPassCity_shouldReturnCityDto() {
        var cityId = 1L;
        var cityName = "city1";
        var city = City.builder()
                .id(cityId)
                .name(cityName)
                .description("city1 description")
                .build();

        CityDto actualCityDto = cityMapper.mapToCityDto(city);

        assertThat(actualCityDto).isNotNull();
        assertThat(actualCityDto.getId()).isEqualTo(cityId);
        assertThat(actualCityDto.getName()).isEqualTo(cityName);
    }

    @Test
    public void updateCity_whenPassCityDtoAndCity_shouldReturnCity() {
        var newCityName = "newCityName";
        var newCityDescription = "new city description";
        var city = City.builder()
                .id(1L)
                .name("cityName")
                .description("city1 description")
                .build();
        var cityDto = CityDto.builder()
                .id(1L)
                .name(newCityName)
                .description(newCityDescription)
                .build();

        City updatedCity = cityMapper.updateCity(cityDto, city);

        assertThat(updatedCity).isNotNull();
        assertThat(updatedCity.getName()).isEqualTo(newCityName);
        assertThat(updatedCity.getDescription()).isEqualTo(newCityDescription);
    }
}
