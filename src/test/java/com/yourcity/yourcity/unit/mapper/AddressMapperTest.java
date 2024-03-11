package com.yourcity.yourcity.unit.mapper;

import com.yourcity.yourcity.YourCityApplication;
import com.yourcity.yourcity.dto.address.AddressDto;
import com.yourcity.yourcity.dto.city.CityDto;
import com.yourcity.yourcity.dto.country.CountryDto;
import com.yourcity.yourcity.dto.mapper.AddressMapper;
import com.yourcity.yourcity.dto.street.StreetDto;
import com.yourcity.yourcity.model.entity.Address;
import com.yourcity.yourcity.model.entity.City;
import com.yourcity.yourcity.model.entity.Country;
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
public class AddressMapperTest {
    AddressMapper addressMapper;

    @Test
    public void mapToAddress_whenPassAddressDto_shouldReturnCorrectAddress() {
        var addressDto = AddressDto.builder()
                .id(1L)
                .countryDto(CountryDto.builder().name("country1").build())
                .cityDto(CityDto.builder().name("city1").build())
                .streetDto(StreetDto.builder().name("street1").build())
                .roomNumber((short) 10)
                .buildingNumber((short) 101)
                .build();
        var addressId = 1L;
        var countryName = "country1";

        Address actualAddress = addressMapper.mapToAddress(addressDto);

        assertThat(actualAddress).isNotNull();
        assertThat(actualAddress.getId()).isEqualTo(addressId);
        assertThat(actualAddress.getCountry().getName()).isEqualTo(countryName);
    }

    @Test
    public void mapToAddressDto_whenPassAddress_shouldReturnCorrectAddressDto() {
        var address = Address.builder()
                .id(1L)
                .country(Country.builder().name("country1").build())
                .city(City.builder().name("city1").build())
                .street(Street.builder().name("street1").build())
                .roomNumber((short) 10)
                .buildingNumber((short) 101)
                .build();
        var addressId = 1L;
        var countryName = "country1";

        AddressDto actualAddressDto = addressMapper.mapToAddressDto(address);

        assertThat(actualAddressDto).isNotNull();
        assertThat(actualAddressDto.getId()).isEqualTo(addressId);
        assertThat(actualAddressDto.getCountryDto().getName()).isEqualTo(countryName);
    }

    @Test
    public void updateAddress_whenPassAddressDtoAndAddress_shouldReturnUpdatedAddress() {
        var address = Address.builder()
                .id(1L)
                .country(Country.builder().name("country1").build())
                .city(City.builder().name("city1").build())
                .street(Street.builder().name("street1").build())
                .roomNumber((short) 10)
                .buildingNumber((short) 101)
                .build();
        var addressDto = AddressDto.builder()
                .id(1L)
                .countryDto(CountryDto.builder().name("country1").build())
                .cityDto(CityDto.builder().name("city1").build())
                .streetDto(StreetDto.builder().name("street1").build())
                .roomNumber((short) 8)
                .buildingNumber((short) 102)
                .build();

        Address updatedAddress = addressMapper.updateAddress(addressDto, address);

        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getRoomNumber()).isEqualTo((short) 8);
        assertThat(updatedAddress.getBuildingNumber()).isEqualTo((short) 102);
    }
}
