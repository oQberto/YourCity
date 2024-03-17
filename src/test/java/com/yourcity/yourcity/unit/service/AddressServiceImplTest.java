package com.yourcity.yourcity.unit.service;

import com.yourcity.yourcity.YourCityApplication;
import com.yourcity.yourcity.dto.address.AddressDto;
import com.yourcity.yourcity.dto.mapper.AddressMapper;
import com.yourcity.yourcity.model.entity.Address;
import com.yourcity.yourcity.repository.AddressRepository;
import com.yourcity.yourcity.service.exception.EntityUpdateException;
import com.yourcity.yourcity.service.impl.AddressServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = YourCityApplication.class)
public class AddressServiceImplTest {
    private Address address;
    private AddressDto addressDto;

    @Mock
    AddressRepository addressRepository;

    @Mock
    AddressMapper addressMapper;

    @InjectMocks
    AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        var id = 1L;
        var buildingNumber = (short) 101;
        var roomNumber = (short) 52;

        address = Address.builder()
                .id(id)
                .buildingNumber(buildingNumber)
                .roomNumber(roomNumber)
                .build();
        addressDto = AddressDto.builder()
                .id(id)
                .buildingNumber(buildingNumber)
                .roomNumber(roomNumber)
                .build();
    }

    @Test
    public void getAddressById_whenIdExists_shouldReturnAddress() {

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressMapper.mapToAddressDto(address)).thenReturn(addressDto);

        AddressDto actualResult = addressService.getAddressById(address.getId());
        InOrder inOrder = inOrder(addressRepository, addressMapper);

        inOrder.verify(addressRepository).findById(anyLong());
        inOrder.verify(addressMapper).mapToAddressDto(any(Address.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(address.getId());
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

        when(addressMapper.mapToAddress(addressDto)).thenReturn(address);
        when(addressMapper.mapToAddressDto(address)).thenReturn(addressDto);
        when(addressRepository.saveAndFlush(address)).thenReturn(address);

        AddressDto actualAddress = addressService.createAddress(addressDto);
        InOrder inOrder = inOrder(addressMapper, addressRepository);

        inOrder.verify(addressMapper).mapToAddress(any(AddressDto.class));
        inOrder.verify(addressRepository).saveAndFlush(any(Address.class));
        inOrder.verify(addressMapper).mapToAddressDto(any(Address.class));

        assertThat(actualAddress).isNotNull();
        assertThat(actualAddress.getId()).isEqualTo(address.getId());
    }

    @Test
    public void updateAddress_whenUpdatedAddressIsCorrect_shouldSaveUpdatedAddress() {
        var updatedAddress = Address.builder()
                .id(1L)
                .buildingNumber((short) 100)
                .roomNumber((short) 52)
                .build();
        var updatedAddressDto = AddressDto.builder()
                .id(1L)
                .buildingNumber((short) 100)
                .roomNumber((short) 52)
                .build();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressMapper.updateAddress(addressDto, address)).thenReturn(updatedAddress);
        when(addressRepository.saveAndFlush(updatedAddress)).thenReturn(updatedAddress);
        when(addressMapper.mapToAddressDto(updatedAddress)).thenReturn(updatedAddressDto);

        AddressDto actualResult = addressService.updateAddress(addressDto);
        InOrder inOrder = inOrder(addressRepository, addressMapper);

        inOrder.verify(addressRepository).findById(anyLong());
        inOrder.verify(addressMapper).updateAddress(any(AddressDto.class), any(Address.class));
        inOrder.verify(addressRepository).saveAndFlush(any(Address.class));
        inOrder.verify(addressMapper).mapToAddressDto(any(Address.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(address.getId());
        assertThat(actualResult.getBuildingNumber()).isEqualTo(updatedAddress.getBuildingNumber());
    }

    @Test
    public void updateAddress_whenCouldNotFindExistedAddress_shouldThrowEntityUpdateException() {

        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.updateAddress(AddressDto.builder().id(Long.MIN_VALUE).build()))
                .isInstanceOf(EntityUpdateException.class)
                .hasMessageContaining(AddressServiceImpl.UPDATE_ADDRESS);

        verify(addressRepository).findById(anyLong());
        verifyNoInteractions(addressMapper);
    }
}
