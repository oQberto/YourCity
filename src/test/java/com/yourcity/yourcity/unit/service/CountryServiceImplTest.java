package com.yourcity.yourcity.unit.service;

import com.yourcity.yourcity.YourCityApplication;
import com.yourcity.yourcity.dto.country.CountryDto;
import com.yourcity.yourcity.dto.mapper.CountryMapper;
import com.yourcity.yourcity.model.entity.Country;
import com.yourcity.yourcity.repository.CountryRepository;
import com.yourcity.yourcity.service.exception.EntityCreationException;
import com.yourcity.yourcity.service.impl.CountryServiceImpl;
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
public class CountryServiceImplTest {
    private Country country;
    private CountryDto countryDto;

    @Mock
    CountryRepository countryRepository;

    @Mock
    CountryMapper countryMapper;

    @InjectMocks
    CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        country = Country.builder()
                .id(1L)
                .name("Country")
                .description("description")
                .build();

        countryDto = CountryDto.builder()
                .id(1L)
                .name("Country")
                .description("description")
                .build();
    }

    @Test
    public void createCity_whenPassedCorrectDto_shouldReturnCreatedCity() {

        when(countryMapper.mapToCountry(countryDto)).thenReturn(country);
        when(countryRepository.saveAndFlush(country)).thenReturn(country);
        when(countryMapper.mapToCountryDto(country)).thenReturn(countryDto);

        CountryDto actualResult = countryService.createCountry(countryDto);
        InOrder inOrder = inOrder(countryRepository, countryMapper);

        inOrder.verify(countryMapper).mapToCountry(countryDto);
        inOrder.verify(countryRepository).saveAndFlush(country);
        inOrder.verify(countryMapper).mapToCountryDto(country);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(country.getId());
    }

    @Test
    public void createCity_whenSaveAndFlushReturnsEmpty_shouldThrowEntityCreationException() {

        when(countryMapper.mapToCountry(countryDto)).thenReturn(country);
        when(countryRepository.saveAndFlush(any(Country.class))).thenReturn(null);

        assertThatThrownBy(() -> countryService.createCountry(countryDto))
                .isInstanceOf(EntityCreationException.class)
                .hasMessageContaining(CountryServiceImpl.COUNTRY_CREATION);

        verify(countryMapper).mapToCountry(any(CountryDto.class));
        verify(countryRepository).saveAndFlush(any(Country.class));
        verify(countryMapper, times(0)).mapToCountryDto(any(Country.class));
    }

    @Test
    public void getCityById_whenCityExists_shouldReturnCity() {
        var id = 1L;

        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        when(countryMapper.mapToCountryDto(country)).thenReturn(countryDto);

        CountryDto actualResult = countryService.getCountryById(id);
        InOrder inOrder = inOrder(countryRepository, countryMapper);

        inOrder.verify(countryRepository).findById(anyLong());
        inOrder.verify(countryMapper).mapToCountryDto(any(Country.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(id);
    }

    @Test
    public void getCityById_whenCityDoesNotExist_shouldThrowEntityNotFoundException() {

        when(countryRepository.findById(Long.MIN_VALUE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> countryService.getCountryById(Long.MIN_VALUE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(CountryServiceImpl.COUNTRY_NOT_FOUND_BY_ID, Long.MIN_VALUE)
                );
    }

    @Test
    public void updateCity() {
        var updatedCountryDto = CountryDto.builder()
                .id(1L)
                .name("newName")
                .description("description")
                .build();
        var updatedCountry = Country.builder()
                .id(updatedCountryDto.getId())
                .name(updatedCountryDto.getName())
                .description(updatedCountryDto.getDescription())
                .build();

        when(countryRepository.findById(updatedCountryDto.getId())).thenReturn(Optional.of(country));
        when(countryMapper.updateCountry(updatedCountryDto, country)).thenReturn(updatedCountry);
        when(countryRepository.saveAndFlush(updatedCountry)).thenReturn(updatedCountry);
        when(countryMapper.mapToCountryDto(updatedCountry)).thenReturn(updatedCountryDto);

        CountryDto actualResult = countryService.updateCountry(updatedCountryDto);
        InOrder inOrder = inOrder(countryRepository, countryMapper);

        inOrder.verify(countryRepository).findById(anyLong());
        inOrder.verify(countryMapper).updateCountry(updatedCountryDto, country);
        inOrder.verify(countryRepository).saveAndFlush(updatedCountry);
        inOrder.verify(countryMapper).mapToCountryDto(updatedCountry);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(updatedCountry.getId());
    }

    @Test
    public void deleteCity_whenCityExists_shouldInvokeDeleteMethod() {

        when(countryRepository.findById(anyLong())).thenReturn(Optional.of(country));

        countryService.deleteCountry(1L);

        verify(countryRepository).delete(country);
    }

    @Test
    public void deleteCity_whenCityDoesNotExist_shouldNotInvokeDeleteMethod() {

        when(countryRepository.findById(anyLong())).thenReturn(Optional.empty());

        countryService.deleteCountry(1L);

        verify(countryRepository, times(0)).delete(any(Country.class));
    }
}
