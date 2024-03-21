package com.yourcity.yourcity.unit.service;

import com.yourcity.yourcity.dto.mapper.PlaceMapper;
import com.yourcity.yourcity.dto.place.PlaceDto;
import com.yourcity.yourcity.model.entity.Place;
import com.yourcity.yourcity.model.entity.enums.PlaceCategory;
import com.yourcity.yourcity.repository.PlaceRepository;
import com.yourcity.yourcity.service.exception.EntityCreationException;
import com.yourcity.yourcity.service.impl.PlaceServiceImpl;
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
public class PlaceServiceImplTest {
    private Place place;
    private PlaceDto placeDto;

    @Mock
    PlaceRepository placeRepository;

    @Mock
    PlaceMapper placeMapper;

    @InjectMocks
    PlaceServiceImpl placeService;

    @BeforeEach
    void setUp() {
        place = Place.builder()
                .id(1L)
                .name("Place")
                .description("description")
                .category(PlaceCategory.CAFFE)
                .build();

        placeDto = PlaceDto.builder()
                .id(1L)
                .name("Place")
                .description("description")
                .category(PlaceCategory.CAFFE)
                .build();
    }

    @Test
    public void createPlace_whenPassedCorrectDto_shouldReturnCreatedPlace() {

        when(placeMapper.mapToPlace(placeDto)).thenReturn(place);
        when(placeRepository.saveAndFlush(place)).thenReturn(place);
        when(placeMapper.mapToPlaceDto(place)).thenReturn(placeDto);

        PlaceDto actualResult = placeService.createPlace(placeDto);
        InOrder inOrder = inOrder(placeRepository, placeMapper);

        inOrder.verify(placeMapper).mapToPlace(placeDto);
        inOrder.verify(placeRepository).saveAndFlush(place);
        inOrder.verify(placeMapper).mapToPlaceDto(place);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(place.getId());
    }

    @Test
    public void createPlace_whenSaveAndFlushReturnsEmpty_shouldThrowEntityCreationException() {

        when(placeMapper.mapToPlace(placeDto)).thenReturn(place);
        when(placeRepository.saveAndFlush(any(Place.class))).thenReturn(null);

        assertThatThrownBy(() -> placeService.createPlace(placeDto))
                .isInstanceOf(EntityCreationException.class)
                .hasMessageContaining(PlaceServiceImpl.CREATE_PLACE);

        verify(placeMapper).mapToPlace(any(PlaceDto.class));
        verify(placeRepository).saveAndFlush(any(Place.class));
        verify(placeMapper, times(0)).mapToPlaceDto(any(Place.class));
    }

    @Test
    public void getPlaceById_whenPlaceExists_shouldReturnPlace() {
        var id = 1L;

        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeMapper.mapToPlaceDto(place)).thenReturn(placeDto);

        PlaceDto actualResult = placeService.getPlaceById(id);
        InOrder inOrder = inOrder(placeRepository, placeMapper);

        inOrder.verify(placeRepository).findById(anyLong());
        inOrder.verify(placeMapper).mapToPlaceDto(any(Place.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(id);
    }

    @Test
    public void getPlaceById_whenPlaceDoesNotExist_shouldThrowEntityNotFoundException() {

        when(placeRepository.findById(Long.MIN_VALUE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.getPlaceById(Long.MIN_VALUE))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(PlaceServiceImpl.GET_PLACE_BY_ID, Long.MIN_VALUE)
                );
    }

    @Test
    public void getPlaceByName_whenPlaceExists_shouldReturnPlace() {
        var name = "Country";

        when(placeRepository.findByName(name)).thenReturn(Optional.of(place));
        when(placeMapper.mapToPlaceDto(place)).thenReturn(placeDto);

        PlaceDto actualResult = placeService.getPlaceByName(name);
        InOrder inOrder = inOrder(placeRepository, placeMapper);

        inOrder.verify(placeRepository).findByName(anyString());
        inOrder.verify(placeMapper).mapToPlaceDto(any(Place.class));

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(place.getId());
    }

    @Test
    public void getPlaceByName_whenPlaceDoesNotExist_shouldThrowEntityNotFoundException() {
        var name = "";
        when(placeRepository.findByName(name)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.getPlaceByName(name))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining(
                        String.format(PlaceServiceImpl.GET_PLACE_BY_NAME, name)
                );
    }

    @Test
    public void getPlacesByCategory_whenPlacesWithPassedCategoryExist_shouldReturnListOfPlaces() {
        Page<Place> places = new PageImpl<>(getPlaces(10));
        Pageable pageable = Pageable.unpaged();

        when(placeRepository.findAllByCategory(PlaceCategory.CAFFE, pageable)).thenReturn(places);
        when(placeMapper.mapToPlaceDto(any(Place.class))).thenReturn(PlaceDto.builder().build());

        List<PlaceDto> actualResult = placeService.getPlacesByCategory(PlaceCategory.CAFFE, pageable);

        assertThat(actualResult).hasSize(10);

        verify(placeRepository).findAllByCategory(PlaceCategory.CAFFE, pageable);
        verify(placeMapper, times(10)).mapToPlaceDto(any(Place.class));
    }

    @Test
    public void updatePlace() {
        var updatedCountryDto = PlaceDto.builder()
                .id(1L)
                .name("newName")
                .description("description")
                .build();
        var updatedCountry = Place.builder()
                .id(updatedCountryDto.getId())
                .name(updatedCountryDto.getName())
                .description(updatedCountryDto.getDescription())
                .build();

        when(placeRepository.findById(updatedCountryDto.getId())).thenReturn(Optional.of(place));
        when(placeMapper.updatePlace(updatedCountryDto, place)).thenReturn(updatedCountry);
        when(placeRepository.saveAndFlush(updatedCountry)).thenReturn(updatedCountry);
        when(placeMapper.mapToPlaceDto(updatedCountry)).thenReturn(updatedCountryDto);

        PlaceDto actualResult = placeService.updatePlace(updatedCountryDto);
        InOrder inOrder = inOrder(placeRepository, placeMapper);

        inOrder.verify(placeRepository).findById(anyLong());
        inOrder.verify(placeMapper).updatePlace(updatedCountryDto, place);
        inOrder.verify(placeRepository).saveAndFlush(updatedCountry);
        inOrder.verify(placeMapper).mapToPlaceDto(updatedCountry);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(updatedCountry.getId());
    }

    @Test
    public void deletePlace_whenPlaceExists_shouldInvokeDeleteMethod() {

        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));

        placeService.deletePlace(1L);

        verify(placeRepository).delete(place);
    }

    @Test
    public void deletePlace_whenPlaceDoesNotExist_shouldNotInvokeDeleteMethod() {

        when(placeRepository.findById(anyLong())).thenReturn(Optional.empty());

        placeService.deletePlace(1L);

        verify(placeRepository, times(0)).delete(any(Place.class));
    }

    private List<Place> getPlaces(int amount) {
        List<Place> places = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            places.add(Place.builder()
                    .id((long) i)
                    .build());
        }

        return places;
    }
}
