package com.yourcity.address.addressservice.service;

import com.yourcity.address.addressservice.model.dto.street.StreetDto;
import com.yourcity.domain.domain.model.enums.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StreetService {

    StreetDto getStreetById(Long id);

    StreetDto getStreetByName(String name);

    StreetDto createStreet(StreetDto dto);

    StreetDto updateStreet(StreetDto dto);


    List<StreetDto> getStreetsByType(Type type, Pageable pageable);


    void deleteStreet(Long id);

}
