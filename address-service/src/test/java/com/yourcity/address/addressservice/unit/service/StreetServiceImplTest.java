package com.yourcity.address.addressservice.unit.service;

import com.yourcity.address.addressservice.model.dto.street.StreetDto;
import com.yourcity.address.addressservice.model.mapper.StreetMapper;
import com.yourcity.address.addressservice.repository.StreetRepository;
import com.yourcity.address.addressservice.service.exception.EntityCreationException;
import com.yourcity.address.addressservice.service.impl.StreetServiceImpl;
import com.yourcity.domain.domain.model.entity.Street;
import com.yourcity.domain.domain.model.enums.Type;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StreetServiceImplTest {
    private Street street;
    private StreetDto streetDto;

    @Mock
    StreetRepository streetRepository;

    @Mock
    StreetMapper streetMapper;

    @InjectMocks
    StreetServiceImpl streetService;

    @BeforeEach
    void setUp() {
        street = Street.builder()
                .id(1L)
                .name("Street")
                .type(Type.BOULEVARD)
                .build();

        streetDto = StreetDto.builder()
                .id(1L)
                .name("Street")
                .type(Type.BOULEVARD)
                .build();
    }

    @Test
    public void createStreet_whenPassedCorrectDto_shouldReturnCreatedStreet() {

        when(streetMapper.mapToStreet(streetDto)).thenReturn(street);
        when(streetRepository.saveAndFlush(street)).thenReturn(street);
        when(streetMapper.mapToStreetDto(street)).thenReturn(streetDto);

        StreetDto actualResult = streetService.createStreet(streetDto);
        InOrder inOrder = inOrder(streetRepository, streetMapper);

        inOrder.verify(streetMapper).mapToStreet(streetDto);
        inOrder.verify(streetRepository).saveAndFlush(street);
        inOrder.verify(streetMapper).mapToStreetDto(street);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(street.getId());
    }

    @Test
    public void createStreet_whenSaveAndFlushReturnsEmpty_shouldThrowEntityCreationException() {

        when(streetMapper.mapToStreet(streetDto)).thenReturn(street);
        when(streetRepository.saveAndFlush(any(Street.class))).thenReturn(null);

        assertThatThrownBy(() -> streetService.createStreet(streetDto))
                .isInstanceOf(EntityCreationException.class)
                .hasMessageContaining(StreetServiceImpl.CREATE_STREET);

        verify(streetMapper).mapToStreet(any(StreetDto.class));
        verify(streetRepository).saveAndFlush(any(Street.class));
        verify(streetMapper, times(0)).mapToStreetDto(any(Street.class));
    }

    @Test
    public void getStreetById_whenStreetExists_shouldReturnStreet() {
        var id = 1L;

        when(streetRepository.findById(id)).thenReturn(Optional.of(street));
        when(streetMapper.mapToStreetDto(street)).thenReturn(streetDto);

        StreetDto actualResult = streetService.getStreetById(id);
        InOrder inOrder = inOrder(streetRepository, streetMapper);

        inOrder.verify(streetRepository).findById(anyLong());
        inOrder.verify(streetMapper).mapToStreetDto(any(Street.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(id);
    }

    @Test
    public void getStreetById_whenStreetDoesNotExist_shouldThrowEntityNotFoundException() {

        when(streetRepository.findById(Long.MIN_VALUE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> streetService.getStreetById(Long.MIN_VALUE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(StreetServiceImpl.GET_STREET_BY_ID, Long.MIN_VALUE)
                );
    }

    @Test
    public void getStreetByName_whenStreetExists_shouldReturnStreet() {
        var name = "Street";

        when(streetRepository.findByName(name)).thenReturn(Optional.of(street));
        when(streetMapper.mapToStreetDto(street)).thenReturn(streetDto);

        StreetDto actualResult = streetService.getStreetByName(name);
        InOrder inOrder = inOrder(streetRepository, streetMapper);

        inOrder.verify(streetRepository).findByName(anyString());
        inOrder.verify(streetMapper).mapToStreetDto(any(Street.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(street.getId());
    }

    @Test
    public void getStreetByName_whenStreetDoesNotExist_shouldThrowEntityNotFoundException() {
        var name = "";
        when(streetRepository.findByName(name)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> streetService.getStreetByName(name))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(StreetServiceImpl.GET_STREET_BY_NAME, name)
                );
    }

    @Test
    public void getStreetsByType_whenStreetsWithPassedTypeExist_shouldReturnListOfStreets() {
        Page<Street> streets = new PageImpl<>(getStreets(10));
        Pageable pageable = Pageable.unpaged();

        when(streetRepository.findAllByType(Type.BOULEVARD, pageable)).thenReturn(streets);
        when(streetMapper.mapToStreetDto(any(Street.class))).thenReturn(StreetDto.builder().build());

        List<StreetDto> actualResult = streetService.getStreetsByType(Type.BOULEVARD, pageable);

        assertThat(actualResult).hasSize(10);

        verify(streetRepository).findAllByType(Type.BOULEVARD, pageable);
        verify(streetMapper, times(10)).mapToStreetDto(any(Street.class));
    }

    @Test
    public void updateStreet() {
        var updatedStreetDto = StreetDto.builder()
                .id(1L)
                .name("newName")
                .type(Type.BOULEVARD)
                .build();
        var updatedStreet = Street.builder()
                .id(updatedStreetDto.getId())
                .name(updatedStreetDto.getName())
                .type(updatedStreetDto.getType())
                .build();

        when(streetRepository.findById(updatedStreetDto.getId())).thenReturn(Optional.of(street));
        when(streetMapper.updateStreet(updatedStreetDto, street)).thenReturn(updatedStreet);
        when(streetRepository.saveAndFlush(updatedStreet)).thenReturn(updatedStreet);
        when(streetMapper.mapToStreetDto(updatedStreet)).thenReturn(updatedStreetDto);

        StreetDto actualResult = streetService.updateStreet(updatedStreetDto);
        InOrder inOrder = inOrder(streetRepository, streetMapper);

        inOrder.verify(streetRepository).findById(anyLong());
        inOrder.verify(streetMapper).updateStreet(updatedStreetDto, street);
        inOrder.verify(streetRepository).saveAndFlush(updatedStreet);
        inOrder.verify(streetMapper).mapToStreetDto(updatedStreet);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(updatedStreet.getId());
    }

    @Test
    public void deleteStreet_whenStreetExists_shouldInvokeDeleteMethod() {

        when(streetRepository.findById(anyLong())).thenReturn(Optional.of(street));

        streetService.deleteStreet(1L);

        verify(streetRepository).delete(street);
    }

    @Test
    public void deleteStreet_whenStreetDoesNotExist_shouldNotInvokeDeleteMethod() {

        when(streetRepository.findById(anyLong())).thenReturn(Optional.empty());

        streetService.deleteStreet(1L);

        verify(streetRepository, times(0)).delete(any(Street.class));
    }

    private List<Street> getStreets(int amount) {
        List<Street> places = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            places.add(Street.builder()
                    .id((long) i)
                    .build());
        }

        return places;
    }
}
