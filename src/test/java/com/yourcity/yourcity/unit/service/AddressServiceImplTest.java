package com.yourcity.yourcity.unit.service;

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
import com.yourcity.yourcity.repository.AddressRepository;
import com.yourcity.yourcity.service.impl.AddressServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = YourCityApplication.class)
public class AddressServiceImplTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    AddressMapper addressMapper;

    @InjectMocks
    AddressServiceImpl addressService;

    @Test
    public void getAddressById_whenIdExists_shouldReturnAddress() {
        var id = 1L;
        var buildingNumber = (short) 101;
        var roomNumber = (short) 52;
        var address = Address.builder()
                .id(id)
                .buildingNumber(buildingNumber)
                .roomNumber(roomNumber)
                .country(Country.builder().build())
                .city(City.builder().build())
                .street(Street.builder().build())
                .build();
        var addressDto = AddressDto.builder()
                .id(id)
                .buildingNumber(buildingNumber)
                .roomNumber(roomNumber)
                .countryDto(CountryDto.builder().build())
                .cityDto(CityDto.builder().build())
                .streetDto(StreetDto.builder().build())
                .build();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressMapper.mapToAddressDto(address)).thenReturn(addressDto);
        AddressDto actualResult = addressService.getAddressById(id);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(id);
    }

    @Test
    public void getAddressById_whenIdDoesNotExist_shouldThrowEntityNotFoundException() {

        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.getAddressById(Long.MIN_VALUE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(AddressServiceImpl.GET_ADDRESS_BY_ID, Long.MIN_VALUE)
                );

        verify(addressRepository).findById(anyLong());
        verifyNoInteractions(addressMapper);
    }

    @Test
    public void createAddress_whenAddressDtoIsCorrect_shouldReturnCreatedAddressDto() {
        var id = 1L;
        var buildingNumber = (short) 101;
        var roomNumber = (short) 52;
        var address = Address.builder()
                .id(id)
                .buildingNumber(buildingNumber)
                .roomNumber(roomNumber)
                .build();
        var addressDto = AddressDto.builder()
                .id(id)
                .buildingNumber(buildingNumber)
                .roomNumber(roomNumber)
                .build();

        when(addressMapper.mapToAddress(addressDto)).thenReturn(address);
        when(addressMapper.mapToAddressDto(address)).thenReturn(addressDto);
        when(addressRepository.saveAndFlush(address)).thenReturn(address);

        addressService.createAddress(addressDto);

        InOrder inOrder = Mockito.inOrder(addressMapper, addressRepository);

        inOrder.verify(addressMapper).mapToAddress(any(AddressDto.class));
        inOrder.verify(addressRepository).saveAndFlush(any(Address.class));
        inOrder.verify(addressMapper).mapToAddressDto(any(Address.class));
    }
}
