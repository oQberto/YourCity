package com.yourcity.yourcity.unit.service;

import com.yourcity.yourcity.YourCityApplication;
import com.yourcity.yourcity.dto.city.CityDto;
import com.yourcity.yourcity.dto.mapper.CityMapper;
import com.yourcity.yourcity.model.entity.City;
import com.yourcity.yourcity.repository.CityRepository;
import com.yourcity.yourcity.service.exception.EntityCreationException;
import com.yourcity.yourcity.service.impl.CityServiceImpl;
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
import static org.mockito.Mockito.*;

@SpringBootTest(classes = YourCityApplication.class)
public class CityServiceImplTest {
    private City city;
    private CityDto cityDto;

    @Mock
    CityRepository cityRepository;

    @Mock
    CityMapper cityMapper;

    @InjectMocks
    CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        city = City.builder()
                .id(1L)
                .name("City")
                .description("description")
                .build();

        cityDto = CityDto.builder()
                .id(1L)
                .name("City")
                .description("description")
                .build();
    }

    @Test
    public void createCity_whenPassedCorrectDto_shouldReturnCreatedCity() {

        when(cityMapper.mapToCity(cityDto)).thenReturn(city);
        when(cityRepository.saveAndFlush(city)).thenReturn(city);
        when(cityMapper.mapToCityDto(city)).thenReturn(cityDto);

        CityDto actualResult = cityService.createCity(cityDto);
        InOrder inOrder = inOrder(cityRepository, cityMapper);

        inOrder.verify(cityMapper).mapToCity(cityDto);
        inOrder.verify(cityRepository).saveAndFlush(city);
        inOrder.verify(cityMapper).mapToCityDto(city);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(city.getId());
    }

    @Test
    public void createCity_whenSaveAndFlushReturnsEmpty_shouldThrowEntityCreationException() {

        when(cityMapper.mapToCity(cityDto)).thenReturn(city);
        when(cityRepository.saveAndFlush(any(City.class))).thenReturn(null);

        assertThatThrownBy(() -> cityService.createCity(cityDto))
                .isInstanceOf(EntityCreationException.class)
                .hasMessageContaining(CityServiceImpl.CITY_CREATION);

        verify(cityMapper).mapToCity(any(CityDto.class));
        verify(cityRepository).saveAndFlush(any(City.class));
        verify(cityMapper, times(0)).mapToCityDto(any(City.class));
    }

    @Test
    public void getCityById_whenCityExists_shouldReturnCity() {
        var id = 1L;

        when(cityRepository.findById(id)).thenReturn(Optional.of(city));
        when(cityMapper.mapToCityDto(city)).thenReturn(cityDto);

        CityDto actualResult = cityService.getCityById(id);
        InOrder inOrder = inOrder(cityRepository, cityMapper);

        inOrder.verify(cityRepository).findById(anyLong());
        inOrder.verify(cityMapper).mapToCityDto(any(City.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(id);
    }

    @Test
    public void getCityById_whenCityDoesNotExist_shouldThrowEntityNotFoundException() {

        when(cityRepository.findById(Long.MIN_VALUE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cityService.getCityById(Long.MIN_VALUE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(CityServiceImpl.CITY_NOT_FOUND, Long.MIN_VALUE)
                );
    }

    @Test
    public void updateCity() {
        var updatedCityDto = CityDto.builder()
                .id(1L)
                .name("newName")
                .description("description")
                .build();
        var updatedCity = City.builder()
                .id(updatedCityDto.getId())
                .name(updatedCityDto.getName())
                .description(updatedCityDto.getDescription())
                .build();

        when(cityRepository.findById(updatedCityDto.getId())).thenReturn(Optional.of(city));
        when(cityMapper.updateCity(updatedCityDto, city)).thenReturn(updatedCity);
        when(cityMapper.mapToCityDto(updatedCity)).thenReturn(updatedCityDto);

        CityDto actualResult = cityService.updateCity(updatedCityDto);
        InOrder inOrder = inOrder(cityRepository, cityMapper);

        inOrder.verify(cityRepository).findById(anyLong());
        inOrder.verify(cityMapper).updateCity(updatedCityDto, city);
        inOrder.verify(cityMapper).mapToCityDto(updatedCity);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(updatedCity.getId());
    }

    @Test
    public void deleteCity_whenCityExists_shouldInvokeDeleteMethod() {

        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

        cityService.deleteCity(1L);

        verify(cityRepository).delete(city);
    }

    @Test
    public void deleteCity_whenCityDoesNotExist_shouldNotInvokeDeleteMethod() {

        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        cityService.deleteCity(1L);

        verify(cityRepository, times(0)).delete(any(City.class));
    }
}
