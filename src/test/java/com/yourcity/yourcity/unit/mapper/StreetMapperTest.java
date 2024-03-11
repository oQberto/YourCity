package com.yourcity.yourcity.unit.mapper;

import com.yourcity.yourcity.YourCityApplication;
import com.yourcity.yourcity.dto.mapper.StreetMapper;
import com.yourcity.yourcity.dto.street.StreetDto;
import com.yourcity.yourcity.model.entity.Street;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
@SpringBootTest(classes = YourCityApplication.class)
public class StreetMapperTest {
    StreetMapper streetMapper;

    @Test
    public void mapToStreet_whenPassStreetDto_shouldReturnStreet() {
        var streetId = 1L;
        var streetName = "street1";
        var streetDto = StreetDto.builder()
                .id(streetId)
                .name(streetName)
                .build();

        Street actualStreet = streetMapper.mapToStreet(streetDto);

        assertThat(actualStreet).isNotNull();
        assertThat(actualStreet.getId()).isEqualTo(streetId);
        assertThat(actualStreet.getName()).isEqualTo(streetName);
    }

    @Test
    public void mapToStreetDto_whenPassStreet_shouldReturnStreetDto() {
        var streetId = 1L;
        var streetName = "street1";
        var street = Street.builder()
                .id(streetId)
                .name(streetName)
                .build();

        StreetDto actualStreet = streetMapper.mapToStreetDto(street);

        assertThat(actualStreet).isNotNull();
        assertThat(actualStreet.getId()).isEqualTo(streetId);
        assertThat(actualStreet.getName()).isEqualTo(streetName);
    }

    @Test
    public void updateStreet_whenPassStreetDtoAndCountry_shouldReturnStreet() {
        var streetId = 1L;
        var newStreetName = "newStreetName";
        var street = Street.builder()
                .id(streetId)
                .name("place1")
                .build();
        var streetDto = StreetDto.builder()
                .id(streetId)
                .name(newStreetName)
                .build();

        Street updatedStreet = streetMapper.updateStreet(streetDto, street);

        assertThat(updatedStreet).isNotNull();
        assertThat(updatedStreet.getName()).isEqualTo(newStreetName);
    }
}
