package com.yourcity.yourcity.service;

import com.yourcity.yourcity.dto.country.CountryDto;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public interface CountryService {

    CountryDto getCountryById(Long id);

    CountryDto getCountryByName(String name);

    CountryDto createCountry(CountryDto dto);

    CountryDto updateCountry(CountryDto dto);

    void deleteCountry(Long id);
}
